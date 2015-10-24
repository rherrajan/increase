package tk.icudi.increase;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;

import tk.icudi.increase.DriverFactory.HTMLDriver;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

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



	public void openPostUrl(String url, Map<String, String> requestParameter, String postBody) throws FailingHttpStatusCodeException, IOException {
		
		HtmlPage page = driver.getPost(url, requestParameter, postBody);
		
		
		System.out.println(" --- asText: " + page.asText());
		System.out.println(" --- asXml: " + page.asXml());
		
	}

}
