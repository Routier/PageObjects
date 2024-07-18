import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardsPage {
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement header = $("[data-test-id=dashboard");
    private SelenideElement reloadButton = $("[data-test-id=action-reload");


    public CardsPage() {
        header.shouldBe(Condition.visible);
    }

    public int getCardBalance(int index) {
        var text = cards.get(index).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(DataHelper.Cards cardInfo) {
        cards.findBy(Condition.attribute("data-test-id", cardInfo.getDataTestId()))
                .$("[data-test-id=action-deposit]").click();
        return new TransferPage();
    }

    public void reloadCardsPage() {
        reloadButton.click();
        header.shouldBe(Condition.visible);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }


}
