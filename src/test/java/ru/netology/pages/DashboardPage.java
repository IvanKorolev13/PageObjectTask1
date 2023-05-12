package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardData;
import ru.netology.data.Transaction;

import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    //private  String xpathForCardTextInDashboard = "//div[@data-test-id]";
    private ElementsCollection cardsList = $$x("//ul[contains(@class,'list')]//div[@data-test-id]");
    private String cssLocatorForReplenishmentButton = "button[data-test-id='action-deposit']";

    public DashboardPage() {
        cardsList.get(0).shouldBe(Condition.appear);
    }

    public CardReplenishmentPage makeTransferTo(Transaction tran) {
        findCardInList(tran.getCardTo()).$(cssLocatorForReplenishmentButton).click();

        return new CardReplenishmentPage();
    }

    public SelenideElement findCardInList(CardData card) {
        String last4DigitOfCardNumber = card.getCardNumber().substring(15);
        for (SelenideElement element : cardsList) {
            if (element.text().contains(last4DigitOfCardNumber)) {
                return element;
            }
        }
        return null;
    }

    public int getCardBalanceOnPage(CardData card) {
        String textInElement = findCardInList(card).text();
        return extractBalance(textInElement);
    }

    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);
        String value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int readAndWriteBalanceOf(CardData card) {
        int balance = getCardBalanceOnPage(card);
        card.setCardAmount(balance);
        return balance;
    }
}
