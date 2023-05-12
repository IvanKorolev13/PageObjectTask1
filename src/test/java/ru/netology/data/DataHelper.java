package ru.netology.data;

import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import ru.netology.data.CardData;
import ru.netology.data.UserData;

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
    public static UserInfo getInvalidUserInfo() {
        return new UserInfo("masha", "QWERTY123");
    }
    @Value
    public static class CardInfo {
        private UserData user;
        private String cardNumber;
        private int cardAmount;
        private SelenideElement replenishmentButton;
    }

    @Value
    public static class Transaction {
        private int id;
        private CardData cardTo;
        private CardData cardFrom;
        private int amount;
    }
}