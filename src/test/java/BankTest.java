import Data.DataHelper;
import Pages.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {
    CardsPage cardsPage;
    DataHelper.Cards firstCardInfo;
    DataHelper.Cards secondCardInfo;

    DataHelper.WrongCard wrongCard;
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
        wrongCard = DataHelper.getWrongCardNumber();

    }

    @Test
    public void transferFromSecondToFirst() {
        var amount = DataHelper.transferAmount(secondCardBalance);

        var expectedFirstCardBalance = firstCardBalance + amount;
        var expectedSecondCardBalance = secondCardBalance - amount;
        var transferPage = cardsPage.selectCardToTransfer(firstCardInfo);
        transferPage.transfer(String.valueOf(amount), secondCardInfo);
        cardsPage.reloadCardsPage();
        var actualFirstCardBalance = cardsPage.getCardBalance(0);
        var actualSecondCardBalance = cardsPage.getCardBalance(1);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void transferFromFirstToSecond() {
        var amount = DataHelper.transferAmount(firstCardBalance);

        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        var transferPage = cardsPage.selectCardToTransfer(secondCardInfo);
        transferPage.transfer(String.valueOf(amount), firstCardInfo);
        cardsPage.reloadCardsPage();
        var actualFirstCardBalance = cardsPage.getCardBalance(0);
        var actualSecondCardBalance = cardsPage.getCardBalance(1);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void errorWrongFromCard(){
        var amount = DataHelper.transferAmount(firstCardBalance);

        var transferPage = cardsPage.selectCardToTransfer(secondCardInfo);
        transferPage.failTransfer(String.valueOf(amount),wrongCard);
        transferPage.error("Ошибка");
    }

    @Test
    public void errorOverLimit(){
        var amount = DataHelper.overLimitAmount(firstCardBalance);

        var transferPage = cardsPage.selectCardToTransfer(secondCardInfo);
        transferPage.transfer(String.valueOf(amount), firstCardInfo);
        transferPage.error("Сумма перевода больше баланса карты");
        cardsPage.reloadCardsPage();
        var actualFirstCardBalance = cardsPage.getCardBalance(0);
        var actualSecondCardBalance = cardsPage.getCardBalance(1);
        assertEquals(cardsPage.getCardBalance(0), actualFirstCardBalance);
        assertEquals(cardsPage.getCardBalance(1), actualSecondCardBalance);

    }

}