package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.UserData;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginInput = $("span[data-test-id='login'] input");
    private final SelenideElement passwordInput = $("span[data-test-id='password'] input");
    private final SelenideElement okButton = $("button[data-test-id='action-login']");

    public VerificationPage login(UserData user) {
        loginInput.setValue(user.getUserLogin());
        passwordInput.setValue(user.getUserPassword());
        okButton.click();

        return new VerificationPage();
    }
}
