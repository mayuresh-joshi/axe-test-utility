package com.psl.axetester;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.deque.axe.AXE;

public class A11YTester {
	private static URL scriptURL;
	
	public static void runTestOn(String pageURL) {
		try {
			scriptURL = new URL("https://cdnjs.cloudflare.com/ajax/libs/axe-core/3.5.5/axe.min.js");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		//System.setProperty("webdriver.chrome.driver", "F://ChromeDriver//chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.get(pageURL);
		System.out.println("Calling axe API...");
		AXE.inject(driver, scriptURL);
		System.out.println("Testing...");
		JSONObject responseJSON = new AXE.Builder(driver, scriptURL).analyze();
		JSONArray violations = responseJSON.getJSONArray("violations");
		if(violations.length() == 0) {
			System.out.println("Accessibility Test Passed.");
		}
		else {
			AXE.writeResults("F:/report", responseJSON);
			System.out.println("Accessibility Test Failed.");
			System.out.println(AXE.report(violations));
		}
	}
}
