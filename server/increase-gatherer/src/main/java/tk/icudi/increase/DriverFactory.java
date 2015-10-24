package tk.icudi.increase;

import java.net.URL;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class DriverFactory {

	private static DriverFactory instance = new DriverFactory();
	private HTMLDriver htmlUnitDriver;

	private static class HTMLDriver extends HtmlUnitDriver {

		private WebClient client;

		public HTMLDriver(BrowserVersion browser) {
			super(browser);
		}

		@Override
		protected WebClient modifyWebClient(WebClient client) {

			this.client = client;
			client.setCookieManager(new CookieManager() {

				private static final long serialVersionUID = 1L;

				@Override
				protected int getPort(java.net.URL url) {
					final int r = super.getPort(url);
					return r != -1 ? r : 80;
				}

			});
			
	        final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) client.getCredentialsProvider();
	        credentialsProvider.addCredentials("vpn", "vpn");
	        
	        System.out.println(" --- credentialsProvider: " + credentialsProvider);
	        
			return super.modifyWebClient(client);
		}

		@Override
		protected void get(URL fullUrl) {
			
			System.out.println(" --- origin: " + fullUrl.getHost());
			
			super.get(fullUrl);
			
			List<NameValuePair> response = client.getCurrentWindow().getEnclosedPage().getWebResponse().getResponseHeaders();
			for (NameValuePair header : response) {
			     System.out.println("  --- " + header.getName() + " = " + header.getValue());
			 }
			
//			System.out.println(" --- origin: " + fullUrl.getHost());
//			
//			if(fullUrl.getHost().contains("ingress.com") == false && fullUrl.getHost().contains("google.com") == false){
//				super.get(fullUrl);
//				return;
//			}
//			
//			
//			try {
//				URL newURL = new URL(fullUrl.getProtocol(), "lienz.lima.zone" , fullUrl.getPort(), fullUrl.getFile());
//				System.out.println(" --- newURL: " + newURL);
//				super.get(newURL);
//				
//			} catch (MalformedURLException e) {
//				
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				super.get(fullUrl);
//			}
			
		}
	}

	public DriverFactory() {

		String applicationName = "Netscape";
		String applicationVersion = "5.0 (X11)";
		String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:39.0) Gecko/20100101 Firefox/39.0";
		int browserVersionNumeric = 36;

		BrowserVersion browser = new BrowserVersion(applicationName, applicationVersion, userAgent, browserVersionNumeric) {

			private static final long serialVersionUID = 1L;

			public boolean hasFeature(BrowserVersionFeatures property) {
				return BrowserVersion.FIREFOX_38.hasFeature(property);
			}
		};

		this.htmlUnitDriver = new HTMLDriver(browser);
		htmlUnitDriver.setJavascriptEnabled(true);
//		htmlUnitDriver.setJavascriptEnabled(false);

		htmlUnitDriver.setProxy("176.31.99.80", 2222);
		
		System.out.println(" --- Proxy Config: " + htmlUnitDriver.client.getOptions().getProxyConfig().getProxyHost());
		System.out.println(" --- isSocksProxy: " + htmlUnitDriver.client.getOptions().getProxyConfig().isSocksProxy());
	}

	public static DriverFactory getInstance() {
		return instance;
	}

	public WebDriver getHTMLUnitDriver() {
		return htmlUnitDriver;
	}

}