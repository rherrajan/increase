package tk.icudi.increase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import com.gargoylesoftware.htmlunit.util.NameValuePair;

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
	        
	        System.out.println(" --- credentialsProvider: " + credentialsProvider);
	        
			return super.modifyWebClient(client);
		}

		@Override
		protected void get(URL fullUrl) {
			
			System.out.println(" --- origin: " + fullUrl.getHost());
			
			super.get(fullUrl);
			
			List<NameValuePair> response = webClient.getCurrentWindow().getEnclosedPage().getWebResponse().getResponseHeaders();
			System.out.println(" --- response: " + response);
			for (NameValuePair header : response) {
			     System.out.println("  --- " + header.getName() + " = " + header.getValue());
			 }
						
		}

		public void getPost(String urlString) throws FailingHttpStatusCodeException, IOException {

			// Instead of requesting the page directly we create a WebRequestSettings object
			WebRequest requestSettings = new WebRequest(new URL(urlString), HttpMethod.POST);

			List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
			requestParams.add(new NameValuePair("Email", "igorschrempf80@gmail.com"));
			
			// Then we set the request parameters
			requestSettings.setRequestParameters(requestParams);

//			Email=igorschrempf80@gmail.com
//					GALX=l0f1BToRogY
//					Passwd=escape01
//					PersistentCookie=yes
//					_utf8=☃
//					bgresponse=!vL9CaZuJ8MAx8qpE_2LECXQfKkoCAAABc1IAAAA0KgDp-Ubi-Y751jw25oTfKkDfHeh4hEvVfUcDacggBMXyNFTxHaEtSRZPxzr1svfh1vv7y8o-9_60mXdBNh0Lp4-NfXDxAQNii9LLgJH3RkFgzhNyhRcKkvyoGjhXVuie8lhU2OPE2eJdj7kXO6SmD7foLq-WSv1rrfsh4cRjIqjURh1rLCUE5v1XnkOrRriEXwkuGnwvHWBLsYKgAkv7nG6S_BNEfZLpvFKfw7_hGUBEI3_mqLNFDpsVhoxFG3dAUCVHSS7rLlwkNIw-u7yhqXx6njW02ZAKnh741nzohTk0F-7dBxXWEciD0UE
//					checkConnection=youtube:589:1
//					checkedDomains=youtube
//					continue=https://appengine.google.com/_ah/conflogin?continue=https://www.ingress.com/intel
//					dnConn=
//					ltmpl=gm
//					pstMsg=1
//					service=ah
//					shdf=ChMLEgZhaG5hbWUaB0luZ3Jlc3MMEgJhaCIUDxXHTvPWkR39qgc9Ntp6RlMnsagoATIUG3HUffbxSU31LjICBdNoinuaikg
//					signIn=Anmelden
					

			// Finally, we can get the page
			HtmlPage page = webClient.getPage(requestSettings);
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
		
		System.out.println(" --- Proxy Config: " + htmlUnitDriver.webClient.getOptions().getProxyConfig().getProxyHost());
		System.out.println(" --- isSocksProxy: " + htmlUnitDriver.webClient.getOptions().getProxyConfig().isSocksProxy());
	}

	public static DriverFactory getInstance() {
		return instance;
	}

	public HTMLDriver getHTMLUnitDriver() {
		return htmlUnitDriver;
	}

}