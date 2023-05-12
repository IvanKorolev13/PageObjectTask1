package ru.netology.data;

import com.codeborne.selenide.SelenideElement;

public class CardData {
    private UserData user;
    private String cardNumber;
    private int cardAmount;
    private SelenideElement replenishmentButton;

    public CardData(UserData user, String cardNumber) {
        this.user = user;
        this.cardNumber = cardNumber;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(int cardAmount) {
        this.cardAmount = cardAmount;
    }

    public SelenideElement getReplenishmentButton() {
        return replenishmentButton;
    }

    public void setReplenishmentButton(SelenideElement replenishmentButton) {
        this.replenishmentButton = replenishmentButton;
    }
}
