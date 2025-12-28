package com.redbus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedBusAutomationAssingnment {

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
		WebElement searchTextBoxElement = wd.switchTo().activeElement(); // give me that textbox!!
		searchTextBoxElement.sendKeys("Mumbai");
		
		
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
	
		//click on the Mumbai option
		for(WebElement location : locationList) {
			String lName = location.getText();
			if(lName.equalsIgnoreCase("Mumbai")) {
				location.click();
				break;
			}
			
		}
		
		// Focus on To section!!
		WebElement toTestBox = wd.switchTo().activeElement();
		toTestBox.sendKeys("Pune");
		
		// Find the search category
		By toSearchCategoryLocator = By.xpath("//div[contains(@class,\"searchCategory\")]");
		List<WebElement> toSearchList = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(toSearchCategoryLocator, 2));
		
		// Find the size of categories and Select the first category
		System.out.println(toSearchList.size());
		WebElement toLocationSearchResult = toSearchList.get(0);
		
		//Find the number of locations appeared
		//chaining of web elements
		By toLocationNameLocator = By.xpath("//div[contains(@class,\"listHeader\")]");
		List<WebElement> toLocationList = toLocationSearchResult.findElements(toLocationNameLocator);
		System.out.println(toLocationList.size());
		
		//click on the Pune option
				for(WebElement toLocation : toLocationList) {
					
					if(toLocation.getText().equalsIgnoreCase("Pune")) {
						toLocation.click();
						break;
					}
					
				}
		
	}

}
