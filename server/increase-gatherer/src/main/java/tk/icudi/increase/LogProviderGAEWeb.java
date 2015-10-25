package tk.icudi.increase;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import tk.icudi.LogProviderWeb;
import tk.icudi.RequestData;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;

public class LogProviderGAEWeb extends LogProviderWeb {

	private WebClient webClient;

	public LogProviderGAEWeb(RequestData data) {
		super(data);

		webClient = new WebClient(BrowserVersion.getDefault());
//		webClient = new WebClient(BrowserVersion.getDefault(),"176.31.99.80", 2222);
		
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.setCookieManager(new CookieManager() {

			private static final long serialVersionUID = 1L;

			@Override
			protected int getPort(java.net.URL url) {
				final int r = super.getPort(url);
				return r != -1 ? r : 80;
			}

		});
	}


	@Override
	protected InputStream provideLogs(Map<String, String> headerParameter, String postBody) throws MalformedURLException, IOException, ProtocolException {
		
		String urlString = "https://lienz.lima.zone/yxorp3.php/https://www.ingress.com/r/getPlexts";


		Page page = openPage(urlString, headerParameter, postBody);

		return page.getWebResponse().getContentAsStream();
	}
	


	
	private Page openPage(String urlString, Map<String, String> headerParameter, String requestBody) throws FailingHttpStatusCodeException, IOException {

		WebRequest requestSettings = new WebRequest(new URL(urlString), HttpMethod.POST);

		for (Entry<String, String> entry : headerParameter.entrySet()) {
			requestSettings.setAdditionalHeader(entry.getKey(), entry.getValue());
		}

		requestSettings.setRequestBody(requestBody);
				
		return webClient.<Page>getPage(requestSettings);
	}
}
