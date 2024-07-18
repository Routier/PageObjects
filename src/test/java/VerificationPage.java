import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement header = $("[data-test-id=code] input");
    private SelenideElement verificationField = $("[data-test-id=code] input");
    private SelenideElement verificationButton = $("[data-test-id=action-verify");

    public VerificationPage() {
        header.shouldBe(Condition.visible);
    }

    public CardsPage verification(DataHelper.VerCode code) {
        verificationField.setValue(code.getVerCode());
        verificationButton.click();
        return new CardsPage();
    }
}
