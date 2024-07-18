
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {
    CardsPage cardsPage;
    DataHelper.Cards firstCardInfo;
    DataHelper.Cards secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getLoginData();
        var authPage = loginPage.login(authInfo);
        var verificationCode = DataHelper.getVerCode();
        cardsPage = authPage.verification(verificationCode);
        firstCardInfo = DataHelper.getFirstCard();
        secondCardInfo = DataHelper.getSecondCard();
        firstCardBalance = cardsPage.getCardBalance(0);
        secondCardBalance = cardsPage.getCardBalance(1);

    }

    @Test
    public void transferFromSecondToFirst() {
        var amount = "5000";
        int s = Integer.parseInt(amount);
        var expectedFirstCardBalance = firstCardBalance + s;
        var expectedSecondCardBalance = secondCardBalance - s;
        var transferPage = cardsPage.selectCardToTransfer(firstCardInfo);
        transferPage.transfer(amount, secondCardInfo);
        cardsPage.reloadCardsPage();
        var actualFirstCardBalance = cardsPage.getCardBalance(0);
        var actualSecondCardBalance = cardsPage.getCardBalance(1);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void transferFromFirstToSecond() {
        var amount = "5000";
        int s = Integer.parseInt(amount);
        var expectedFirstCardBalance = firstCardBalance - s;
        var expectedSecondCardBalance = secondCardBalance + s;
        var transferPage = cardsPage.selectCardToTransfer(secondCardInfo);
        transferPage.transfer(amount, firstCardInfo);
        cardsPage.reloadCardsPage();
        var actualFirstCardBalance = cardsPage.getCardBalance(0);
        var actualSecondCardBalance = cardsPage.getCardBalance(1);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void wrongCardNumber() {
        var transferPage = cardsPage.selectCardToTransfer(secondCardInfo);
        transferPage.failToTransfer("1000", "5559000000000003");
    }
}
