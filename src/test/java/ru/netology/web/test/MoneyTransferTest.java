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
        val firstCardBalanceBefore = dashboardPage.getFirstCardBalance();
        val secondCardBalanceBefore = dashboardPage.getLastCardBalance();
        sum = 100;
        val transferPage = DashboardPage.clickToTransferCard1();
        transferPage.validTransferToFirst(sum);
        val firstCardBalanceAfter = DataHelper.getBalanceAfterReplenishment(firstCardBalanceBefore, sum);
        val secondCardBalanceAfter = DataHelper.getBalanceAfterWritingOff(secondCardBalanceBefore, sum);
        val firstCardCurrentBalance = dashboardPage.getFirstCardBalance();
        val secondCardCurrentBalance = dashboardPage.getLastCardBalance();
        assertEquals(firstCardBalanceAfter, firstCardCurrentBalance);
        assertEquals(secondCardBalanceAfter, secondCardCurrentBalance);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        val firstCardBalanceBefore = dashboardPage.getFirstCardBalance();
        val secondCardBalanceBefore = dashboardPage.getLastCardBalance();
        sum = 100;
        val transferPage = DashboardPage.clickToTransferCard2();
        transferPage.validTransferToSecond(sum);
        val secondCardBalanceAfter = DataHelper.getBalanceAfterReplenishment(secondCardBalanceBefore, sum);
        val firstCardBalanceAfter = DataHelper.getBalanceAfterWritingOff(firstCardBalanceBefore, sum);
        val firstCardCurrentBalance = dashboardPage.getFirstCardBalance();
        val secondCardCurrentBalance = dashboardPage.getLastCardBalance();
        assertEquals(firstCardBalanceAfter, firstCardCurrentBalance);
        assertEquals(secondCardBalanceAfter, secondCardCurrentBalance);
    }


    @Test
    void shouldNotTransferMoreThanAvailableFromFirstCard() {
        val firstCardBalanceBefore = dashboardPage.getFirstCardBalance();
        sum = firstCardBalanceBefore + 100;
        val transferPage = DashboardPage.clickToTransferCard2().unsuccessfulTransferCard1(sum);
    }

    @Test
    void shouldNotTransferMoreThanAvailableFromSecondCard() {
        val secondCardBalanceBefore = dashboardPage.getLastCardBalance();
        sum = secondCardBalanceBefore + 100;
        val transferPage = DashboardPage.clickToTransferCard2().unsuccessfulTransferCard2(sum);
    }
}

