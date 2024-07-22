package Pages;
import Data.DataHelper;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login");

    public void LoginPage() {
        $("[data-test-id = login] input").shouldBe(Condition.visible);
    }

    public VerificationPage login(DataHelper.LoginData loginInfo) {
        loginField.setValue(loginInfo.getLogin());
        passwordField.setValue(loginInfo.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}
