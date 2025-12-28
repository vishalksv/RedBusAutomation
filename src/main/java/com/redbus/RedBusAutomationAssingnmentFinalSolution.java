package com.redbus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedBusAutomationAssingnmentFinalSolution {

	public static void main(String[] args) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--start-maximized");
		WebDriver wd = new ChromeDriver(chromeOptions);
		WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(30));

		// Visit the redbus.com website
		wd.get("https://www.redbus.in/");

		By sourceButtonLocator = By.xpath("//div[contains(@class,'srcDestWrapper') and position()=1]");
		WebElement sourceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(sourceButtonLocator));
		sourceButton.click();

		By searchSuggestionSectionLocator = By.xpath("//div[contains(@class,\"searchSuggestionWrapper\")]");
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(searchSuggestionSectionLocator));
		selectLocation(wd, wait, "Mumbai"); // From location
		selectLocation(wd, wait, "Pune"); // To location

		By seachButtonLocator = By.xpath("//button[contains(@class,\"searchButtonWrapper\")]");
		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(seachButtonLocator));
		searchButton.click();
		
		By closeButtonLocator = By.xpath("//button[@aria-label=\"Close\"]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(closeButtonLocator)).click();
		
		By primoButtonLocator = By.xpath("//div[contains(text(),\"Primo Bus\")]");
		WebElement primoButton = wait.until(ExpectedConditions.elementToBeClickable(primoButtonLocator));
		primoButton.click();

		By eveningButtonLocator = By.xpath("//div[contains(text(),\"18:00-24:00\")]");
		WebElement eveningButton = wait.until(ExpectedConditions.elementToBeClickable(eveningButtonLocator));
		eveningButton.click();

		By subtileLocator = By.xpath("//div[contains(@class,\"busesFoundText\")]");
		String initialText = wait.until(ExpectedConditions.visibilityOfElementLocated(subtileLocator)).getText();

		wait.until(ExpectedConditions
				.not(ExpectedConditions.textToBePresentInElementLocated(subtileLocator, initialText)));

		WebElement subTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(subtileLocator));

		System.out.println(subTitle.getText());

		By tuppleWrapperLocator = By.xpath("//li[contains(@class,\"tupleWrapper\")]");
		By busesNameLocator = By.xpath(".//div[contains(@class,\"travelsName\")]");
		
		JavascriptExecutor js = (JavascriptExecutor) wd;

		while (true) {
			List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
			List<WebElement> endOfList = wd.findElements(By.xpath("//span[contains(text(),\"End of list\")]"));
			js.executeScript("arguments[0].scrollIntoView({behavior:'smooth'})", rowList.get(rowList.size()-1));
			if (!endOfList.isEmpty()) {
				break;
			}
			
		}
		List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
		for (WebElement rows : rowList) {
			System.out.println(rows.findElement(busesNameLocator).getText());
		}
		
		System.out.println("Number of buses loaded "+rowList.size());

	}

	public static void selectLocation(WebDriver wd, WebDriverWait wait, String locationName) {
		WebElement searchTextBoxElement = wd.switchTo().activeElement();
		searchTextBoxElement.sendKeys(locationName);

		By searchCategoryLocator = By.xpath("//div[contains(@class,\"searchCategory\")]");
		List<WebElement> searchList = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLocator, 2));

		System.out.println(searchList.size());
		WebElement locationSearchResult = searchList.get(0);

		By locationNameLocator = By.xpath("//div[contains(@class,\"listHeader\")]");
		List<WebElement> locationList = locationSearchResult.findElements(locationNameLocator);
		System.out.println(locationList.size());

		for (WebElement location : locationList) {
			String lName = location.getText();
			if (lName.equalsIgnoreCase(locationName)) {
				location.click();
				break;
			}

		}
	}

}
