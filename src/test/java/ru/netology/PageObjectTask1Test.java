package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.DataHelper.CardInfo;
import ru.netology.data.DataHelper.UserInfo;
import ru.netology.data.DataHelper.VerificationCode;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PageObjectTask1Test {
    UserInfo validUser1 = DataHelper.getAuthInfo();
    CardInfo card1 = DataHelper.getCard1();
    CardInfo card2 = DataHelper.getCard2();
    CardInfo nonExistentCard = DataHelper.getNonExistentCard();
    VerificationCode verificationCode = DataHelper.getVerificationTestCode();

    DashboardPage dashboardPage;
    int startBalanceOfCard1;
    int startBalanceOfCard2;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        new LoginPage()
                .login(validUser1)
                .acceptCode(verificationCode.getTestCode());
        dashboardPage = new DashboardPage();

        int difference =
                dashboardPage.getCardBalanceOnPage(card1) - dashboardPage.getCardBalanceOnPage(card2);
        if (difference > 0) {
            dashboardPage
                    .makeTransferTo(card2)
                    .makeTransferFromAndAmount(card1, difference / 2);
        } else if (difference < 0) {
            dashboardPage
                    .makeTransferTo(card1)
                    .makeTransferFromAndAmount(card2, difference / 2);
        }
        startBalanceOfCard1 = dashboardPage.getCardBalanceOnPage(card1);
        startBalanceOfCard2 = dashboardPage.getCardBalanceOnPage(card2);
    }

    @Test
    public void testTransferBetweenCardsOfOneUser() {
        int transactionAmount = 9999;

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(card1)
                .makeTransferFromAndAmount(card2, transactionAmount);

        assertEquals(startBalanceOfCard1 + transactionAmount, dashboardPage.getCardBalanceOnPage(card1));
        assertEquals(startBalanceOfCard2 - transactionAmount, dashboardPage.getCardBalanceOnPage(card2));
    }

    @Test
    public void testTransferEntireAmountOfCard() {
        int transactionAmount = 10000;

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(card2)
                .makeTransferFromAndAmount(card1, transactionAmount);

        assertEquals(startBalanceOfCard2 + transactionAmount, dashboardPage.getCardBalanceOnPage(card2));
        assertEquals(startBalanceOfCard1 - transactionAmount, dashboardPage.getCardBalanceOnPage(card1));
    }

    @Test
    public void testTransferZero() {
        int transactionAmount = 0;

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(card1)
                .makeTransferFromAndAmount(card2, transactionAmount);

        assertEquals(startBalanceOfCard1 + transactionAmount, dashboardPage.getCardBalanceOnPage(card1));
        assertEquals(startBalanceOfCard2 - transactionAmount, dashboardPage.getCardBalanceOnPage(card2));
    }

    /**
     * bug: card balance is negative
     */

    public void testTransferAmountGreaterThanBalance() {
        int transactionAmount = 10001;

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(card1)
                .makeTransferFromAndAmount(card2, transactionAmount);

        // не знаю как должна реагировать программа на попытку пополнить сумму большую, чем остаток

        assertEquals(startBalanceOfCard1, dashboardPage.getCardBalanceOnPage(card1));
        assertEquals(startBalanceOfCard2, dashboardPage.getCardBalanceOnPage(card2));
    }

    @Test
    public void testTransferFromNonExistentCard() {
        int transactionAmount = 1;

        dashboardPage = new DashboardPage();
        dashboardPage
                .makeTransferTo(card1)
                .makeTransferWithErrorCard(nonExistentCard, transactionAmount);

        assertEquals(startBalanceOfCard1, dashboardPage.getCardBalanceOnPage(card1));
    }
}