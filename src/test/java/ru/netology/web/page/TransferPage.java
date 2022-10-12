package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement sumField = $("div[data-test-id=amount] input");

    private SelenideElement accountField = $("span[data-test-id=from] input");
    private SelenideElement transferButton = $("button[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id = error-notification]");


    public DashboardPage transferSum(DataHelper.CardNumber info, int value) {
        sumField.setValue(String.valueOf(value));
        accountField.setValue(info.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void checkBalance(DataHelper.CardNumber info, int value) {
        transferSum(info, value);
        errorNotification.shouldHave(exactText("На карте № " + info.getCardNumber() + " недостаточно средств")).shouldBe(visible);
    }
}
