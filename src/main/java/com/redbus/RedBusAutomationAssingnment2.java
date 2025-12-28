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

public class RedBusAutomationAssingnment2 {

	public static void main(String[] args) throws InterruptedException {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--start-maximized");
		// I want to launch the browser!! ChromeBrowser
		WebDriver wd = new ChromeDriver(chromeOptions);
		WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(30));

		// Visit the redbus.com website
		wd.get("https://www.redbus.in/");
		//wd.manage().window().maximize(); 
		
		By sourceButtonLocator = By.xpath("//div[contains(@class,'srcDestWrapper') and position()=1]");
		WebElement sourceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(sourceButtonLocator));
		sourceButton.click();
		
//		Thread.sleep(4000);
		By searchSuggestionSectionLocator = By.xpath("//div[contains(@class,\"searchSuggestionWrapper\")]");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(searchSuggestionSectionLocator));
		selectLocation(wd, wait,"Mumbai"); // From location
		selectLocation(wd, wait,"Pune"); // To location
		
		// Click on the search button
		By seachButtonLocator = By.xpath("//button[contains(@class,\"searchButtonWrapper\")]");
		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(seachButtonLocator));
		searchButton.click();
		
//		//Select boarding as Panvel
//		By boardingLocator = By.xpath("//div[contains(text(),\"Panvel\")]");
//		WebElement boardingPoint = wait.until(ExpectedConditions.elementToBeClickable(boardingLocator));
//		boardingPoint.click();
		
		//Click on Proceed
//		By proceedLocator = By.xpath("//div[contains(@class,\"footerBtn\")]");
//		WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(proceedLocator));
//		proceedButton.click();
		
		// Select the Primo from the filters
		By primoButtonLocator = By.xpath("//div[contains(text(),\"Primo Bus\")]");
		WebElement primoButton = wait.until(ExpectedConditions.elementToBeClickable(primoButtonLocator));
		primoButton.click();
		
		
		// select the evening filter it is given as 18:00-24:00
		By eveningButtonLocator = By.xpath("//div[contains(text(),\"18:00-24:00\")]");
		WebElement eveningButton = wait.until(ExpectedConditions.elementToBeClickable(eveningButtonLocator));
		eveningButton.click();
		
		By subtileLocator = By.xpath("//div[contains(@class,\"busesFoundText\")]");
		String initialText = wait.until(ExpectedConditions.visibilityOfElementLocated(subtileLocator)).getText();
		
		// Wait until text is NOT the initial text (i.e., it changed)
		wait.until(ExpectedConditions.not(
		    ExpectedConditions.textToBePresentInElementLocated(subtileLocator, initialText)
		));
		
		// Use ExpectedConditons.textToBePresentInElementLocated(subTitleLocator, "buses") if required
		// Find the number of available 
		WebElement subTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(subtileLocator));
//		if(wait.until(ExpectedConditions.textToBePresentInElementLocated(subtileLocator, "buses"))) {
//			subTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(subtileLocator));
//			
//		}
		System.out.println(subTitle.getText());
		
		By tuppleWrapperLocator = By.xpath("//li[contains(@class,\"tupleWrapper\")]");
		By busesNameLocator = By.xpath(".//div[contains(@class,\"travelsName\")]");
		List<WebElement> rowList;
		//List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
		//System.out.println(rowList.size());
		//for(WebElement rows : rowList) {
		//	System.out.println(rows.findElement(busesNameLocator).getText());
		//}
		
		// How to scroll in selenium webdriver
		JavascriptExecutor js = (JavascriptExecutor)wd;
		//js.executeScript("arguments[0].scrollIntoView({behaviour:'smooth'})", rowList.get(rowList.size()-3));
		//Thread.sleep(5000);
		//List<WebElement> newRowList = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tuppleWrapperLocator, rowList.size()));
		//System.out.println("Total number of buses"+newRowList.size());
		
		// How to perform lazy loading
		while(true) {
			rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
			List<WebElement> endOfList = wd.findElements(By.xpath("//span[contains(text(),\"End of list\")]"));
			
			if(!endOfList.isEmpty()) {
				break;
			}
			js.executeScript("arguments[0].scrollIntoView({behavior:'smooth'})", rowList.get(rowList.size()-3));
		}
		for(WebElement rows : rowList) {
			System.out.println(rows.findElement(busesNameLocator).getText());
		}
		
	}

	public static void selectLocation(WebDriver wd, WebDriverWait wait, String locationName) {
		WebElement searchTextBoxElement = wd.switchTo().activeElement(); // give me that textbox!!
		searchTextBoxElement.sendKeys(locationName);
		
		
		// Find the search category
		By searchCategoryLocator = By.xpath("//div[contains(@class,\"searchCategory\")]");
		List<WebElement> searchList = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLocator, 2));
		
		
		// Find the size of categories and Select the first category
		System.out.println(searchList.size());
		WebElement locationSearchResult = searchList.get(0);
		
		
		//Find the number of locations appeared
		//chaining of web elements
		By locationNameLocator = By.xpath("//div[contains(@class,\"listHeader\")]");
		List<WebElement> locationList = locationSearchResult.findElements(locationNameLocator);
		System.out.println(locationList.size());
	
		//click on the locationName option
		for(WebElement location : locationList) {
			String lName = location.getText();
			if(lName.equalsIgnoreCase(locationName)) {
				location.click();
				break;
			}
			
		}
	}

}
