package tk.icudi.increase;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;

import tk.icudi.increase.DriverFactory.HTMLDriver;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Crawler {



	private HTMLDriver driver;

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



	public void openPostUrl(String url) throws FailingHttpStatusCodeException, IOException {
		
		driver.getPost(url);
		

	}

}
