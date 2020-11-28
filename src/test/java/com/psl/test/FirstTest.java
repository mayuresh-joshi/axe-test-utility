package com.psl.test;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.deque.axe.AXE;


public class FirstTest {
	//private static final URL scriptURL = I.class.getResource("/src/test/resources/axe.min.js");
	private static URL scriptURL;
	
	@Test
	public void testOne() {
		try {
			//scriptURL = new File("F:/axe.min.js").toURI().toURL();
			scriptURL = new URL("https://cdnjs.cloudflare.com/ajax/libs/axe-core/3.5.5/axe.min.js");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setProperty("webdriver.chrome.driver", "F://ChromeDriver//chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com");
		AXE.inject(driver, scriptURL);
		System.out.println("Injected.......");
		JSONObject responseJSON = new AXE.Builder(driver, scriptURL).analyze();
		JSONArray violations = responseJSON.getJSONArray("violations");
		if(violations.length() == 0) {
			Assert.assertTrue("No Violations Found", true);
		}
		else {
			AXE.writeResults("F:/report", responseJSON);
			Assert.assertTrue(AXE.report(violations), false);
		}
	}
}
