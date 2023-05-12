package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardData;
import ru.netology.data.UserData;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.LoginPage;
import ru.netology.data.Transaction;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PageObjectTask1Test {
    private UserData validUser1 = new UserData("vasya", "qwerty123");
    private CardData validUser1Card1 = new CardData(validUser1, "5559 0000 0000 0001");
    private CardData validUser1Card2 = new CardData(validUser1, "5559 0000 0000 0002");
    private CardData validUser1Card3 = new CardData(validUser1, "1111 0000 0000 0000");
    private final static String VERIFICATION_CODE_TEST = "12345";
    Transaction tran;
    DashboardPage dashboardPage;
    int startBalanceOfValidUser1Card1;
    int startBalanceOfValidUser1Card2;

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
            tran = new Transaction(0, validUser1Card2, validUser1Card1, difference / 2);
            dashboardPage.makeTransferTo(tran).makeTransferFromAndAmount(tran);
        } else if (difference < 0) {
            tran = new Transaction(0, validUser1Card1, validUser1Card2, -difference / 2
            );
            dashboardPage.makeTransferTo(tran).makeTransferFromAndAmount(tran);
        }
        startBalanceOfValidUser1Card1 = dashboardPage.readAndWriteBalanceOf(validUser1Card1);
        startBalanceOfValidUser1Card2 = dashboardPage.readAndWriteBalanceOf(validUser1Card2);
    }

    @Test
    public void testTransferBetweenCardsOfOneUser() {
        int transactionAmount = 9999;
        tran = new Transaction(1, validUser1Card1, validUser1Card2, transactionAmount);

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(tran)
                .makeTransferFromAndAmount(tran);

        assertEquals(
                startBalanceOfValidUser1Card1 + transactionAmount,
                dashboardPage.getCardBalanceOnPage(validUser1Card1))
        ;
        assertEquals(
                startBalanceOfValidUser1Card2 - transactionAmount,
                dashboardPage.getCardBalanceOnPage(validUser1Card2)
        );
    }

    @Test
    public void testTransferEntireAmountOfCard() {
        int transactionAmount = 10000;
        tran = new Transaction(1, validUser1Card1, validUser1Card2, transactionAmount);

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(tran)
                .makeTransferFromAndAmount(tran);

        assertEquals(
                startBalanceOfValidUser1Card1 + transactionAmount,
                dashboardPage.getCardBalanceOnPage(validUser1Card1))
        ;
        assertEquals(
                startBalanceOfValidUser1Card2 - transactionAmount,
                dashboardPage.getCardBalanceOnPage(validUser1Card2)
        );
    }
    /**
     * bug: card balance is negative
     */
    @Test
    public void testTransferAmountGreaterThanBalance() {
        int transactionAmount = 10001;
        tran = new Transaction(3, validUser1Card1, validUser1Card2, transactionAmount);

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(tran)
                .makeTransferFromAndAmount(tran);

        assertEquals(
                startBalanceOfValidUser1Card1,
                dashboardPage.getCardBalanceOnPage(validUser1Card1))
        ;
        assertEquals(
                startBalanceOfValidUser1Card2,
                dashboardPage.getCardBalanceOnPage(validUser1Card2)
        );
    }

    @Test
    public void testTransferFromNonExistentCard() {
        int transactionAmount = 1;
        tran = new Transaction(1, validUser1Card1, validUser1Card3, transactionAmount);

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(tran)
                .makeTransferWithErrorCard(tran);

        assertEquals(
                startBalanceOfValidUser1Card1 + transactionAmount,
                dashboardPage.getCardBalanceOnPage(validUser1Card1))
        ;
        assertEquals(
                startBalanceOfValidUser1Card2 - transactionAmount,
                dashboardPage.getCardBalanceOnPage(validUser1Card2)
        );
    }

}