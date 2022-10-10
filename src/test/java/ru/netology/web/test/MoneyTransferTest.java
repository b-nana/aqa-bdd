package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;


public class MoneyTransferTest {
    int card1Balance1;
    int card2Balance1;
    int card1Balance2;
    int card2Balance2;
    int sum;
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        card1Balance1 = dashboardPage.getBalance(dashboardPage.card1);
        card2Balance1 = dashboardPage.getBalance(dashboardPage.card2);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        sum = 100;
        val transferPage = dashboardPage.clickTransfer(dashboardPage.card1);
        val cardNum = DataHelper.getSecondCard().getNumber();
        val dashboardPage2 = transferPage.successfulTransfer(Integer.toString(sum), cardNum);
        card1Balance2 = dashboardPage2.getBalance(dashboardPage2.card1);
        card2Balance2 = dashboardPage2.getBalance(dashboardPage2.card2);
        assertEquals(card1Balance1 + sum, card1Balance2);
        assertEquals(card2Balance1 - sum, card2Balance2);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        sum = 100;
        val transferPage = dashboardPage.clickTransfer(dashboardPage.card2);
        val cardNum = DataHelper.getFirstCard().getNumber();
        val dashboardPage2 = transferPage.successfulTransfer(Integer.toString(sum), cardNum);
        card1Balance2 = dashboardPage2.getBalance(dashboardPage2.card1);
        card2Balance2 = dashboardPage2.getBalance(dashboardPage2.card2);
        assertEquals(card1Balance1 - sum, card1Balance2);
        assertEquals(card2Balance1 + sum, card2Balance2);
    }

    @Test
    void shouldNotTransferMoreThanAvailable() {
        sum = card1Balance1 + 100;
        val transferPage = dashboardPage.clickTransfer(dashboardPage.card2);
        val cardNum = DataHelper.getFirstCard().getNumber();
        transferPage.unsuccessfulTransfer(Integer.toString(sum), cardNum);
    }
}

