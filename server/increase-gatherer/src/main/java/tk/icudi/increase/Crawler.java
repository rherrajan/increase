package tk.icudi.increase;

import org.openqa.selenium.WebDriver;

import com.gargoylesoftware.htmlunit.WebClient;

public class Crawler {



	private WebDriver driver;

	public Crawler() {

		this.driver = DriverFactory.getInstance().getHTMLUnitDriver();

		// driver.get("https://www.ingress.com/intel");
		driver.get("https://duckduckgo.com/?q=Permission+denied");

		// driver.get("http://duckduckgo.com:80");

		// driver.get("https://www.google.de");
		// driver.get("http://www.google.de");

		System.out.println(" --- driver: " + driver.getCurrentUrl());

		// page instances init()
		// loginPage = PageFactory.initElements(driver, LoginPage.class);
		// homePage = PageFactory.initElements(driver, FacebookUserPage.class);

	}

	public void updateData() {
		// TODO Auto-generated method stub

	}

	public String getSourcecode() {
		return driver.getPageSource();
	}

}
