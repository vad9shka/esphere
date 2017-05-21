package Main;

import Base.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainClass extends Config {

    private WebElement webElement;

    By element = By.cssSelector(".twitter-typeahead");
    By inputText = By.cssSelector(".tt-input");
    By buttonClick = By.cssSelector(".input-group-btn");
    By mask  = By.cssSelector(".overlay-show");
    By itemsList = By.cssSelector(".search.ng-scope");
    By innLocator = By.xpath(".//div[@class='gray']//*[@ng-bind='item.инн']");
    By ogrnLocator = By.xpath(".//div[@class='gray']//*[@ng-bind='item.огрн']");
    By iconLogoLocator = By.cssSelector(".icon.i-logo");
    By rowsTableLocator = By.xpath(".//*[@class='content grid']/div[@class='row ng-scope']");

    public void annotationBlock(){
        try {
            Actions actions = new Actions(driver);
            webElement = driver.findElement(element);
            actions.moveToElement(webElement).build().perform();
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

    public void search(String text){
        try {
            webElement = driver.findElement(inputText);
            webElement.sendKeys(text);

            webElement = driver.findElement(buttonClick);
            webElement.click();
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

    public void waitPreloader(){
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(mask));
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

    public List<WebElement> list(){
        List<WebElement> items = driver.findElements(itemsList);
        return items;
    }

    public boolean findName(String text){

        boolean flag = false;

        try {
            search(text);
            waitPreloader();

            //поиск на присутствие на странице
            List<WebElement> items = list();

            for(int i=0; i<items.size(); i++){
                String innerText = items.get(i).findElement(By.tagName("a")).getText();
                if(innerText.contains(text)){
                    flag = true;
                    break;
                }
            }

        }
        catch (WebDriverException e){
            e.printStackTrace();
            Assert.fail();
        }
        catch (Throwable e){
            e.printStackTrace();
            Assert.fail();
        }

        return flag;
    }

    public boolean findNameUser(String firstName, String lastName, String middleName){

        boolean flag = false;

        String text = firstName + " " + lastName + " " + middleName;

        search(text);
        waitPreloader();

        //поиск
        List<WebElement> items = list();

        for(int i=0; i<items.size(); i++){
            String innerText = items.get(i).findElement(By.tagName("a")).getText();
            if(innerText.contains(firstName) || innerText.contains(lastName) || innerText.contains(middleName)){
                flag = true;
                break;
            }
        }

        return flag;
    }

    public boolean findINN(long innNumber){

        boolean flag = false;

        search(String.valueOf(innNumber));
        waitPreloader();

        //поиск
        List<WebElement> items = list();

        for(int i=0; i<items.size(); i++){
            String innerText = items.get(i).findElement(innLocator).getText();
            if(innerText.equals(String.valueOf(innNumber))){
                flag = true;
                break;
            }
        }

        return flag;
    }

    public boolean findOGRN(long ogrnNumber){
        boolean flag = false;

        search(String.valueOf(ogrnNumber));
        waitPreloader();

        //поиск
        List<WebElement> items = list();

        for(int i=0; i<items.size(); i++){
            String innerText = items.get(i).findElement(ogrnLocator).getText();
            if(innerText.equals(String.valueOf(ogrnNumber))){
                flag = true;
                break;
            }
        }

        return flag;
    }

    public void clearTextField(){
        webElement = driver.findElement(inputText);
        webElement.clear();
    }

    public void clickContentLogo(){
        webElement = driver.findElement(iconLogoLocator);
        webElement.click();
    }

    public boolean elementNotPresent(){
        boolean notExist;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            notExist = driver.findElements(rowsTableLocator).isEmpty();
        }
        finally {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }

        return notExist;
    }

}
