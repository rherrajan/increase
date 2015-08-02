package tk.icudi.increase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.javascript.host.URL;

public class DriverFactory {

	private static DriverFactory instance = new DriverFactory();
	private HtmlUnitDriver htmlUnitDriver;

	public DriverFactory() {

		String applicationName = "Netscape";
		String applicationVersion = "5.0 (X11)";
		String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:39.0) Gecko/20100101 Firefox/39.0";
		int browserVersionNumeric = 36;

		BrowserVersion browser = new BrowserVersion(applicationName, applicationVersion, userAgent, browserVersionNumeric) {

			private static final long serialVersionUID = 1L;

			public boolean hasFeature(BrowserVersionFeatures property) {

				// change features here
				return BrowserVersion.FIREFOX_38.hasFeature(property);
			}
		};

		this.htmlUnitDriver = new HtmlUnitDriver(browser) {
			@Override
			protected WebClient newWebClient(BrowserVersion version) {

				WebClient webClient = super.newWebClient(version);

				webClient.setCookieManager(new CookieManager() {
					
					private static final long serialVersionUID = 1L;

					@Override
					protected int getPort(java.net.URL url) {
						final int r = super.getPort(url);
						return r != -1 ? r : 80;
					}
					

				});
				return webClient;
			}
		};
		htmlUnitDriver.setJavascriptEnabled(true);
	}

	public static DriverFactory getInstance() {
		return instance;
	}

	public WebDriver getHTMLUnitDriver() {
		return htmlUnitDriver;
	}

}
