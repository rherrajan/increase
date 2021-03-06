//package tk.icudi.increase;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertThat;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.openqa.selenium.WebElement;
//
//import tk.icudi.LogEntry;
//import tk.icudi.PlextParser;
//import tk.icudi.RequestDataPrivate;
//
//public class CrawlerSystemTest {
//
//	@Test
//	public void should_see_login_button() throws Exception {
//		Crawler crawler = new Crawler();
//		crawler.openUrl("https://www.ingress.com/intel");
//		
//		LoginPage loginPage = crawler.getLoginPage();
//		
//		WebElement loginButton = loginPage.getLoginButton();
//		
//		assertThat(loginButton.toString(), containsString("ingress.com"));
//	}
//
//
//	@Test
//	public void should_get_plexus_logs_from_credentials_in_GAE() throws Exception {
//
//		List<LogEntry> logs = PlextParser.getLogsFromProvider(new LogProviderGAEWeb(new RequestDataPrivate()));
//
//		assertThat(logs.size(), is(50));
//	}
//
//}
