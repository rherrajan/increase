package tk.icudi.increase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;

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
			protected WebClient modifyWebClient(WebClient client) {
				client.setCookieManager(new CookieManager() {
					
					private static final long serialVersionUID = 1L;

					@Override
					protected int getPort(java.net.URL url) {
						final int r = super.getPort(url);
						return r != -1 ? r : 80;
					}
					

				});
				return super.modifyWebClient(client);
			}
		};
		htmlUnitDriver.setJavascriptEnabled(true);
		
//		htmlUnitDriver.setProxy("109.71.138.13", 10080);
//		htmlUnitDriver.setProxy("5.9.152.210", 42659);
//		htmlUnitDriver.setProxy("176.117.72.166", 63118);
//		htmlUnitDriver.setProxy("104.236.27.173", 10080);	
//		htmlUnitDriver.setProxy("183.246.69.39", 1080);	
		
//		htmlUnitDriver.setProxy("220.132.54.171", 80);	
//		htmlUnitDriver.setProxy("115.29.151.135", 8080);	
//		htmlUnitDriver.setProxy("218.92.227.172", 23685);	
		htmlUnitDriver.setProxy("218.92.227.168", 17130);	
		
		
		
	}

	public static DriverFactory getInstance() {
		return instance;
	}

	public WebDriver getHTMLUnitDriver() {
		return htmlUnitDriver;
	}

}
