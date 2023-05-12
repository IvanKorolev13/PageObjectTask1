package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class CardReplenishmentPage {
    private final SelenideElement amountInput = $("span[data-test-id='amount'] input");
    private final SelenideElement fromCardNumberInput = $("span[data-test-id='from'] input");
    private final SelenideElement okButton = $("button[data-test-id='action-transfer']");
    private final SelenideElement escapeButton = $("button[data-test-id='action-cancel']");
    private final SelenideElement errorNotification = $("div[class='notification__content']");

    public DashboardPage makeTransferFromAndAmount(CardInfo card, int amount) {
        amountInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        amountInput.setValue(amount + "");
        fromCardNumberInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        fromCardNumberInput.setValue(card.getCardNumber());
        okButton.click();

        return new DashboardPage();
    }

    public DashboardPage makeTransferWithErrorCard(CardInfo card, int amount) {
        amountInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        amountInput.setValue(amount + "");
        fromCardNumberInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        fromCardNumberInput.setValue(card.getCardNumber());
        okButton.click();

        errorNotification
                .shouldBe(Condition.appear, Duration.ofSeconds(5))
                .shouldHave(Condition.text("Ошибк"));

        escapeButton.click();

        return new DashboardPage();
    }

    public CardReplenishmentPage() {
        amountInput.shouldBe(Condition.appear);
    }
}
