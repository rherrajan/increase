package tk.icudi.increase;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DriverFactory {

	private static DriverFactory instance = new DriverFactory();
	private HTMLDriver htmlUnitDriver;

	public static class HTMLDriver extends HtmlUnitDriver {

		private WebClient webClient;

		public HTMLDriver(BrowserVersion browser) {
			super(browser);
		}

		@Override
		protected WebClient modifyWebClient(WebClient client) {

			this.webClient = client;
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
	        
			return super.modifyWebClient(client);
		}

		@Override
		protected void get(URL fullUrl) {
			super.get(fullUrl);
		}

		public HtmlPage getPost(String urlString, Map<String, String> requestParameter, String requestBody) throws FailingHttpStatusCodeException, IOException {

			WebRequest requestSettings = new WebRequest(new URL(urlString), HttpMethod.POST);

			for (Entry<String, String> entry : requestParameter.entrySet()) {
				requestSettings.setAdditionalHeader(entry.getKey(), entry.getValue());
			}

			requestSettings.setRequestBody(requestBody);
			
			HtmlPage page = webClient.getPage(requestSettings);
			return page;
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
//				return BrowserVersion.FIREFOX_38.hasFeature(property);
				return BrowserVersion.getDefault().hasFeature(property);
			}
		};

		this.htmlUnitDriver = new HTMLDriver(browser);
		htmlUnitDriver.setJavascriptEnabled(true);
//		htmlUnitDriver.setJavascriptEnabled(false);

		// htmlUnitDriver.setProxy("176.31.99.80", 2222);
	}

	public static DriverFactory getInstance() {
		return instance;
	}

	public HTMLDriver getHTMLUnitDriver() {
		return htmlUnitDriver;
	}

}