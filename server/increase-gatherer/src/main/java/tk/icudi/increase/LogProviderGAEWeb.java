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
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;

public class LogProviderGAEWeb extends LogProviderWeb {

	private WebClient webClient;

	public LogProviderGAEWeb(RequestData data) {
		super(data);

		webClient = new WebClient(BrowserVersion.getDefault(),"176.31.99.80", 2222);
	}


	@Override
	protected InputStream provideLogs(String plextURL, Map<String, String> requestParameter, String postBody) throws MalformedURLException, IOException, ProtocolException {
		Page page = openPage(plextURL, requestParameter, postBody);

		return page.getWebResponse().getContentAsStream();
	}
	

	  
	private Page openPage(String urlString, Map<String, String> requestParameter, String requestBody) throws FailingHttpStatusCodeException, IOException {

		WebRequest requestSettings = new WebRequest(new URL(urlString), HttpMethod.POST);

		for (Entry<String, String> entry : requestParameter.entrySet()) {
			requestSettings.setAdditionalHeader(entry.getKey(), entry.getValue());
		}

		requestSettings.setRequestBody(requestBody);
				
		return webClient.<Page>getPage(requestSettings);
	}
}
