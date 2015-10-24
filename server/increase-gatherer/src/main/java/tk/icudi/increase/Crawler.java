package tk.icudi.increase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.gargoylesoftware.htmlunit.WebClient;

public class Crawler {



	private WebDriver driver;

	public Crawler() {

		this.driver = DriverFactory.getInstance().getHTMLUnitDriver();
	}
	
	

	public void updateData() {
		// TODO Auto-generated method stub

	}

	public String getSourcecode() {
		return driver.getPageSource();
	}

	public LoginPage getLoginPage() {
		return PageFactory.initElements(driver, LoginPage.class);
	}



	public void openUrl(String url) {

		driver.get(url);

		System.out.println(" --- url: " + driver.getCurrentUrl());
		System.out.println(" --- pagesource: " + driver.getPageSource());

	}

}
