package ru.netologi.webselenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {


    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    public void meetingSuccessfullyScheduled() {
        open("http://localhost:9999");
        $("[data-test-id='city']input").setValue("Волгоград");
        String planningDate = generateDate(4, "dd.mm.yyyy");
        $("[date-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[date-test-id='date'] input").setValue(planningDate);
        $("[date-test-id='name'] input").setValue("Иванов Иван");
        $("[date-test-id='phone'] input").setValue("+79995555555");
        $("[date-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }
}
