package tk.icudi.increase;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.WebElement;

public class CrawlerSystemTest {

	@Test
	public void should_see_login_button() throws Exception {
		Crawler crawler = new Crawler();
		crawler.openUrl("https://www.ingress.com/intel");
		
		System.out.println(" --- src: " + crawler.getSourcecode());
		
		
		LoginPage loginPage = crawler.getLoginPage();
		
		WebElement loginButton = loginPage.getLoginButton();
		
		System.out.println(" --- loginButton: " + loginButton);
		
		assertThat(loginButton.toString(), containsString("ingress.com"));
	}


}
