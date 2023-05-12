package ru.netology.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class UserInfo {
        private String userLogin;
        private String userPassword;
    }
    public static UserInfo getValidUserInfo() {
        return new UserInfo("vasya", "qwerty123");
    }

    @Value
    public static class CardInfo {
        private ru.netology.data.UserInfo user;
        private String cardNumber;
        private int cardAmount;
    }

    @Value
    public static class Transaction {
        private int id;
        private ru.netology.data.CardInfo cardTo;
        private ru.netology.data.CardInfo cardFrom;
        private int amount;
    }
}