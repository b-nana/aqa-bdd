package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }


    @Value
    public static class CardNumber {
        private String id;
        private String cardNumber;
    }

    public static CardNumber getCard1() {
        return new CardNumber("0", "5559 0000 0000 0001");
    }

    public static CardNumber getCard2() {
        return new CardNumber("1", "5559 0000 0000 0002");
    }

    public static int getBalanceAfterWritingOff(int balance, int sum) {
        int currentBalance = balance - sum;
        return currentBalance;
    }

    public static int getBalanceAfterReplenishment(int balance, int sum) {
        int currentBalance = balance + sum;
        return currentBalance;
    }


}