package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
import ru.netology.data.UserInfo;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PageObjectTask1Test {
    private UserInfo validUser1 = new UserInfo("vasya", "qwerty123");
    private CardInfo validUser1Card1 = new CardInfo(validUser1, "5559 0000 0000 0001");
    private CardInfo validUser1Card2 = new CardInfo(validUser1, "5559 0000 0000 0002");
    private CardInfo validUser1Card3 = new CardInfo(validUser1, "1111 0000 0000 0000");
    private final static String VERIFICATION_CODE_TEST = "12345";
    DashboardPage dashboardPage;
    int startBalanceOfCard1;
    int startBalanceOfCard2;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        new LoginPage()
                .login(validUser1)
                .acceptCode(VERIFICATION_CODE_TEST);
        dashboardPage = new DashboardPage();

        int difference =
                dashboardPage.readAndWriteBalanceOf(validUser1Card1) - dashboardPage.readAndWriteBalanceOf(validUser1Card2);
        if (difference > 0) {
            dashboardPage
                    .makeTransferTo(validUser1Card2)
                    .makeTransferFromAndAmount(validUser1Card1, difference / 2);
        } else if (difference < 0) {
            dashboardPage
                    .makeTransferTo(validUser1Card1)
                    .makeTransferFromAndAmount(validUser1Card2, difference / 2);
        }
        startBalanceOfCard1 = dashboardPage.readAndWriteBalanceOf(validUser1Card1);
        startBalanceOfCard2 = dashboardPage.readAndWriteBalanceOf(validUser1Card2);
    }

    @Test
    public void testTransferBetweenCardsOfOneUser() {
        int transactionAmount = 9999;

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(validUser1Card1)
                .makeTransferFromAndAmount(validUser1Card2, transactionAmount);

        assertEquals(startBalanceOfCard1 + transactionAmount, dashboardPage.getCardBalanceOnPage(validUser1Card1));
        assertEquals(startBalanceOfCard2 - transactionAmount, dashboardPage.getCardBalanceOnPage(validUser1Card2));
    }

    @Test
    public void testTransferEntireAmountOfCard() {
        int transactionAmount = 10000;

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(validUser1Card1)
                .makeTransferFromAndAmount(validUser1Card2, transactionAmount);

        assertEquals(startBalanceOfCard1 + transactionAmount, dashboardPage.getCardBalanceOnPage(validUser1Card1));
        assertEquals(startBalanceOfCard2 - transactionAmount, dashboardPage.getCardBalanceOnPage(validUser1Card2));
    }

    @Test
    public void testTransferZero() {
        int transactionAmount = 0;

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(validUser1Card1)
                .makeTransferFromAndAmount(validUser1Card2, transactionAmount);

        assertEquals(startBalanceOfCard1 + transactionAmount, dashboardPage.getCardBalanceOnPage(validUser1Card1));
        assertEquals(startBalanceOfCard2 - transactionAmount, dashboardPage.getCardBalanceOnPage(validUser1Card2));
    }

    /**
     * bug: card balance is negative
     */

    public void testTransferAmountGreaterThanBalance() {
        int transactionAmount = 10001;

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(validUser1Card1)
                .makeTransferFromAndAmount(validUser1Card2, transactionAmount);

        // не знаю как должна реагировать программа на попытку пополнить сумму большую, чем остаток

        assertEquals(startBalanceOfCard1, dashboardPage.getCardBalanceOnPage(validUser1Card1));
        assertEquals(startBalanceOfCard2, dashboardPage.getCardBalanceOnPage(validUser1Card2));
    }

    @Test
    public void testTransferFromNonExistentCard() {
        int transactionAmount = 1;

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(validUser1Card1)
                .makeTransferWithErrorCard(validUser1Card3, transactionAmount);

        assertEquals(startBalanceOfCard1, dashboardPage.getCardBalanceOnPage(validUser1Card1));
    }
}