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

    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        val firstCardBalanceBefore = dashboardPage.getCardBalance("0");
        val secondCardBalanceBefore = dashboardPage.getCardBalance("1");
        sum = 100;
        val secondCardInfo = DataHelper.getCard2();
        val transferPage = dashboardPage.chooseCard(0);
        transferPage.transferSum(secondCardInfo, sum);
        val firstCardBalanceAfter = DataHelper.getBalanceAfterReplenishment(firstCardBalanceBefore, sum);
        val secondCardBalanceAfter = DataHelper.getBalanceAfterWritingOff(secondCardBalanceBefore, sum);
        val firstCardCurrentBalance = dashboardPage.getCardBalance("0");
        val secondCardCurrentBalance = dashboardPage.getCardBalance("1");
        assertEquals(firstCardBalanceAfter, firstCardCurrentBalance);
        assertEquals(secondCardBalanceAfter, secondCardCurrentBalance);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        val firstCardBalanceBefore = dashboardPage.getCardBalance("0");
        val secondCardBalanceBefore = dashboardPage.getCardBalance("1");
        sum = 100;
        val firstCardInfo = DataHelper.getCard1();
        val transferPage = dashboardPage.chooseCard(1);
        transferPage.transferSum(firstCardInfo, sum);
        val secondCardBalanceAfter = DataHelper.getBalanceAfterReplenishment(secondCardBalanceBefore, sum);
        val firstCardBalanceAfter = DataHelper.getBalanceAfterWritingOff(firstCardBalanceBefore, sum);
        val firstCardCurrentBalance = dashboardPage.getCardBalance("0");
        val secondCardCurrentBalance = dashboardPage.getCardBalance("1");
        assertEquals(firstCardBalanceAfter, firstCardCurrentBalance);
        assertEquals(secondCardBalanceAfter, secondCardCurrentBalance);
    }

    @Test
    void shouldNotTransferMoreThanAvailable1() {
        val firstCardBalanceBefore = dashboardPage.getCardBalance("0");
        sum = firstCardBalanceBefore + 100;
        val transferPage = dashboardPage.chooseCard(0);
        val firstCardInfo = DataHelper.getCard1();
        transferPage.checkBalance(firstCardInfo, sum);
    }

    @Test
    void shouldNotTransferMoreThanAvailable2() {
        val secondCardBalanceBefore = dashboardPage.getCardBalance("1");
        sum = secondCardBalanceBefore + 100;
        val transferPage = dashboardPage.chooseCard(1);
        val secondCardInfo = DataHelper.getCard2();
        transferPage.checkBalance(secondCardInfo, sum);
    }


}

