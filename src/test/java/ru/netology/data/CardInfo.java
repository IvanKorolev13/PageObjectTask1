package ru.netology.data;

public class CardInfo {
    private UserInfo user;
    private String cardNumber;
    private int cardAmount;

    public CardInfo(UserInfo user, String cardNumber) {
        this.user = user;
        this.cardNumber = cardNumber;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
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
}
