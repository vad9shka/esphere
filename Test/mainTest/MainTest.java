package mainTest;


import Main.MainClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MainTest extends MainClass {

    WebElement webElement;

    By annotationLocator = By.cssSelector(".search.hint");

    @Test
    public void test1(){
        try {
            String nameCompany = "тест";
            Assert.assertTrue(findName(nameCompany));
            clearTextField();

            String firstName = "Иван";
            String lastName = "Иванов";
            String middleName = "Иванович";
            Assert.assertTrue(findNameUser(firstName, lastName, middleName));
            clearTextField();

            long inn = 2235004141L;
            Assert.assertTrue(findINN(inn));
            clearTextField();

            long ogrn = 1022201983473L;
            Assert.assertTrue(findOGRN(ogrn));

            annotationBlock();
            webElement = driver.findElement(annotationLocator);
            String display = webElement.getAttribute("style");
            if(!display.contains("display: block;")){
                Assert.fail("Block is not displayed");
            }

            clickContentLogo();
            Assert.assertTrue(elementNotPresent());

        }
        catch (WebDriverException e){
            e.printStackTrace();
            Assert.fail();
        }
        catch (Throwable e){
            e.printStackTrace();
            Assert.fail();
        }
    }
}
