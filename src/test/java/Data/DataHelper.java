package Data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    public static LoginData getLoginData() {
        return new LoginData("vasya", "qwerty123");
    }

    public static VerCode getVerCode() {
        return new VerCode("12345");
    }


    public static Cards getFirstCard() {
        return new Cards("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0001");
    }

    public static Cards getSecondCard() {
        return new Cards("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559 0000 0000 0002");
    }

    public static WrongCard getWrongCardNumber() {
        return new WrongCard("5559 0000 0000 0003");
    }

    public static int transferAmount(int balance) {
        return new Random().nextInt(Math.abs(balance)) + 1;
    }

    public static int overLimitAmount(int balance) {
        return Math.abs(balance) + 50_000;
    }

    @Value
    public static class LoginData {
        String login;
        String password;
    }

    @Value
    public static class VerCode {
        public String verCode;
    }

    @Value
    public static class Cards {
        String dataTestId;
        String cardNumber;
    }

    @Value
    public static class WrongCard {
        String wrongCardNumber;
    }

}
