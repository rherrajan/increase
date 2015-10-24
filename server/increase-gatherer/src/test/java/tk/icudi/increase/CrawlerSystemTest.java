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

	@Test
	public void should_get_plexus_logs_from_credentials() throws Exception {
		
		List<LogEntry> logs = getLogsFromProvider(new LogProviderWeb(new RequestDataRherrajan()));

		assertThat(logs.size(), is(50));
	}
	
	private List<LogEntry> getLogsFromProvider(LogProvider provider) throws IOException {
		PlextParser parser = new PlextParser(provider);
		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();
		return logs;
	}
}
