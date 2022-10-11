package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement sumField = $("div[data-test-id=amount] input");

    private SelenideElement accountField = $("span[data-test-id=from] input");
    private SelenideElement transferButton = $("button[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id = error-notification]");

    public DashboardPage validTransferToFirst(int sum) {
        sumField.setValue(String.valueOf(sum));
        accountField.setValue(String.valueOf(DataHelper.getCard2()));
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage validTransferToSecond(int sum) {
        sumField.setValue(String.valueOf(sum));
        accountField.setValue(String.valueOf(DataHelper.getCard1()));
        transferButton.click();
        return new DashboardPage();
    }


    public DashboardPage unsuccessfulTransferCard1(int sum) {
        sumField.setValue(String.valueOf(sum));
        accountField.setValue(String.valueOf(DataHelper.getCard1()));
        transferButton.click();
        errorNotification.shouldBe(visible);
        return new DashboardPage();
    }

    public DashboardPage unsuccessfulTransferCard2(int sum) {
        sumField.setValue(String.valueOf(sum));
        accountField.setValue(String.valueOf(DataHelper.getCard2()));
        transferButton.click();
        errorNotification.shouldBe(visible);
        return new DashboardPage();
    }
}
