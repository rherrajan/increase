package tk.icudi.increase;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.WebElement;

public class CrawlerSystemTest {

	@Test
	public void test() throws Exception {
		Crawler crawler = new Crawler();
		System.out.println(" --- src: " + crawler.getSourcecode());
		
		
		LoginPage loginPage = crawler.getLoginPage();
		
		WebElement loginButton = loginPage.getLoginButton();
		
		System.out.println(" --- loginButton: " + loginButton);
	}

}
