package ru.netology.data;

public class UserData {
    private String userLogin;
    private String userPassword;

    public UserData(String userLogin, String userPassword) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }

    public UserData(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
