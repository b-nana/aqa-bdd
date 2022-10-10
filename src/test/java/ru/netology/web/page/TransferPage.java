package ru.netology.web.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement sumField = $("div[data-test-id=amount] input");
    private SelenideElement accountField = $("span[data-test-id=from] input");
    private SelenideElement transferButton = $("button[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id = error-notification]");

    public DashboardPage successfulTransfer(String sum, String cardNum) {
        sumField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        sumField.setValue(sum);
        accountField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        accountField.setValue(cardNum);
        transferButton.click();
        return new DashboardPage();
    }

    public void unsuccessfulTransfer(String sum, String cardNum) {
        sumField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        sumField.setValue(sum);
        errorNotification.shouldBe(visible);
    }
}
