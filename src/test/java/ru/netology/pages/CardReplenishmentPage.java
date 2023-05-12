package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.Transaction;

import static com.codeborne.selenide.Selenide.$;

public class CardReplenishmentPage {
    private final SelenideElement amountInput = $("span[data-test-id='amount'] input");
    private final SelenideElement fromCardNumberInput = $("span[data-test-id='from'] input");
    private final SelenideElement okButton = $("button[data-test-id='action-transfer']");
    private final SelenideElement escapeButton = $("button[data-test-id='action-cancel']");
    private final SelenideElement errorNotification = $("div[class='notification__content']");

    public DashboardPage makeTransferFromAndAmount(Transaction tran) {
        amountInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        amountInput.setValue(tran.getAmount() + "");
        fromCardNumberInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        fromCardNumberInput.setValue(tran.getCardFrom().getCardNumber());
        okButton.click();

        return new DashboardPage();
    }

    public CardReplenishmentPage makeTransferWithErrorCard(Transaction tran) {
        amountInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        amountInput.setValue(tran.getAmount() + "");
        fromCardNumberInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        fromCardNumberInput.setValue(tran.getCardFrom().getCardNumber());
        okButton.click();

        errorNotification.shouldBe(Condition.appear);

        return this;
    }

    public CardReplenishmentPage() {
        amountInput.shouldBe(Condition.appear);
    }
}
