package tk.icudi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class LogProviderWebProxy extends LogProviderWeb {

	public LogProviderWebProxy(RequestData data) {
		super(data);
	}

	@Override
	protected InputStream provideLogs(Map<String, String> requestParameter, String postBody) throws MalformedURLException, IOException, ProtocolException {
		HttpURLConnection connection = createInputStream(requestParameter, postBody);
		return connection.getInputStream();
	}

	@Override
	protected HttpURLConnection createInputStream(Map<String, String> requestParameter, String postBody) throws MalformedURLException, IOException, ProtocolException {
		URL plexts = new URL(getPlextURL());
		HttpURLConnection connection = (HttpURLConnection) plexts.openConnection();
		return connection;
	}

	@Override
	protected String getPlextURL() {
		return "http://lienz.lima.zone/plext.php?version=" + data.v + "&csrftoken=" + data.csrftoken + "&sacsid=" + data.sacsid;
	}

}
