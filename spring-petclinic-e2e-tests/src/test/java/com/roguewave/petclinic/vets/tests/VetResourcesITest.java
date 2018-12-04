package com.roguewave.petclinic.vets.tests;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VetResourcesITest {

    @Value("${baseUrl}")
    public String baseUrl;


    @Test
    public void testNavigationForward() {
        beforeTest();
        open(baseUrl + "/#!/welcome");

        System.out.println("");
        $(By.tagName("title")).shouldBe(exist);

        assertEquals(baseUrl + "/#!/welcome", url());

        String cssSelector = "[title='veterinarians']";
        assertTrue($(By.cssSelector(cssSelector)).exists());
        $(By.cssSelector(cssSelector)).click();

        $(By.tagName("title")).shouldBe(exist);
        assertEquals(baseUrl + "/#!/vets", url());
    }

    @Test
    public void testVetsAreLoaded() {
        beforeTest();
        open(baseUrl + "/#!/vets");
        sleep(5000);
        assertEquals(6, $$("[ng-repeat='vet in $ctrl.vetList']").size());
    }

    @Test
    public void testOwnersEditOwner() {
        beforeTest();
        open(baseUrl + "/#!/welcome");

        String ownersSelector = "[class='dropdown']";
        assertTrue($(ownersSelector).exists());
        $(ownersSelector).click();

        String allOwnersSelector = "[ui-sref='owners']";
        assertTrue($(allOwnersSelector).exists());

        $(allOwnersSelector).click();
        sleep(3000);
        String GeorgeFranklin = "[href='#!/owners/details/2']";
        assertTrue($(GeorgeFranklin).exists());
        $(GeorgeFranklin).click();
    }

    public void beforeTest() {
        //Temporary workaround for Zuul not wiring first request properly
        open(baseUrl);
        sleep(5000);
        open(baseUrl + "/#!/vets");
        sleep(5000);
        open(baseUrl + "/#!/owners");
        sleep(5000);
        open(baseUrl);
    }

    @Test
    public void testVetDetails(){
        open(baseUrl + "/#!/vets");

        String JamesCarter = "James Carter";
        $(Selectors.byText(JamesCarter)).should(Condition.exist);
        $(Selectors.byText(JamesCarter)).click();

        $(Selectors.byText("Veterinarians")).should(Condition.exist);
        $(Selectors.byText(JamesCarter)).should(Condition.exist);
    }
}