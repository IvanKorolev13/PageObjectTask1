package ru.netology.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    public static String VERIFICATION_CODE_TEST = "12345";

    @Value
    public static class UserInfo {
        private String userLogin;
        private String userPassword;
    }

    public static UserInfo getAuthInfo() {
        return new UserInfo("vasya", "qwerty123");
    }

    @Value
    public static class CardInfo {
        private UserInfo user;
        private String cardNumber;
    }
}
