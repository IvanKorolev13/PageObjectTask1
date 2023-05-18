package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.DataHelper.CardInfo;
import ru.netology.data.DataHelper.UserInfo;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.VERIFICATION_CODE_TEST;

class PageObjectTask1Test {
    UserInfo validUser1 = DataHelper.getAuthInfo();
    CardInfo validUser1Card1 = new CardInfo(validUser1, "5559 0000 0000 0001");
    CardInfo validUser1Card2 = new CardInfo(validUser1, "5559 0000 0000 0002");
    CardInfo validUser1Card3 = new CardInfo(validUser1, "1111 0000 0000 0000");

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
                dashboardPage.getCardBalanceOnPage(validUser1Card1) - dashboardPage.getCardBalanceOnPage(validUser1Card2);
        if (difference > 0) {
            dashboardPage
                    .makeTransferTo(validUser1Card2)
                    .makeTransferFromAndAmount(validUser1Card1, difference / 2);
        } else if (difference < 0) {
            dashboardPage
                    .makeTransferTo(validUser1Card1)
                    .makeTransferFromAndAmount(validUser1Card2, difference / 2);
        }
        startBalanceOfCard1 = dashboardPage.getCardBalanceOnPage(validUser1Card1);
        startBalanceOfCard2 = dashboardPage.getCardBalanceOnPage(validUser1Card2);
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
                .makeTransferTo(validUser1Card2)
                .makeTransferFromAndAmount(validUser1Card1, transactionAmount);

        assertEquals(startBalanceOfCard2 + transactionAmount, dashboardPage.getCardBalanceOnPage(validUser1Card2));
        assertEquals(startBalanceOfCard1 - transactionAmount, dashboardPage.getCardBalanceOnPage(validUser1Card1));
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