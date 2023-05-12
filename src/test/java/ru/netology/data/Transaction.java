package ru.netology.data;

public class Transaction {
    private int id;
    private CardData cardTo;
    private CardData cardFrom;
    private int amount;

    public Transaction(int id, CardData cardTo, CardData cardFrom, int amount) {
        this.id = id;
        this.cardTo = cardTo;
        this.cardFrom = cardFrom;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CardData getCardTo() {
        return cardTo;
    }

    public void setCardTo(CardData cardTo) {
        this.cardTo = cardTo;
    }

    public CardData getCardFrom() {
        return cardFrom;
    }

    public void setCardFrom(CardData cardFrom) {
        this.cardFrom = cardFrom;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
