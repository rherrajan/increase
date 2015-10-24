package tk.icudi.increase;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import tk.icudi.LogEntry;
import tk.icudi.LogProvider;
import tk.icudi.LogProviderWeb;
import tk.icudi.PlextParser;
import tk.icudi.RequestDataRherrajan;

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
