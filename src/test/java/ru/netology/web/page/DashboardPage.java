package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item");

    private SelenideElement transferCard1 = $("div[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button[data-test-id=action-deposit]");
    private SelenideElement transferCard2 = $("div[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button[data-test-id=action-deposit]");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }


    public static TransferPage clickToTransferCard1() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.transferCard1.click();
        return new TransferPage();
    }

    public static TransferPage clickToTransferCard2() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.transferCard2.click();
        return new TransferPage();
    }

    public int getFirstCardBalance() {
        val text = cards.first().text();
        return extractBalance(text);
    }

    public int getLastCardBalance() {
        val text = cards.last().text();
        return extractBalance(text);
    }

    public int getCardBalance() {
        val text = $$("[data-test-id]").filter(visible).shouldHave(texts(", баланс: "));
        return extractBalance(String.valueOf(text));
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

}
