import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement header = $("[data-test-id =amount] input");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer");
    private SelenideElement errorMessage = $("[data-test-id=error-notification] .notification__content");

    public TransferPage() {
        header.shouldBe(Condition.visible);
    }

    public void transfer(String amount, DataHelper.Cards cardInfo) {
        amountField.setValue(amount);
        fromField.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

    public void error() {
        errorMessage.shouldHave(text("Ошибка"), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void failToTransfer(String i, String number) {
        amountField.setValue(i);
        fromField.setValue(number);
        transferButton.click();
        error();
    }
}
