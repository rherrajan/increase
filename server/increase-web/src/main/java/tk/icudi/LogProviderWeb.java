package tk.icudi;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

public class LogProviderWeb implements LogProvider {

	protected RequestData data;

	public LogProviderWeb() {
	}

	public LogProviderWeb(RequestData data) {
		this.data = data;
	}

	private void assertInputComplete() {

		if (data == null) {
			throw new IllegalArgumentException("All Input-Parameters are missing");
		}

		if (data.csrftoken == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: csrftoken");
		}

		if (data.sacsid == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: sacsid");
		}

		if (data.v == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: v");
		}

	}

	@Override
	public InputStream provideLogs() throws IOException {

		assertInputComplete();

		Map<String, String> requestParameter = getRequestParameter();
		String postBody = getOutputParameters();

		return provideLogs(requestParameter, postBody);
	}

	protected InputStream provideLogs(Map<String, String> requestParameter, String postBody) throws MalformedURLException, IOException, ProtocolException {

		HttpURLConnection connection = createInputStream(requestParameter, postBody);

		return new GZIPInputStream(connection.getInputStream());
	}

	protected HttpURLConnection createInputStream(Map<String, String> requestParameter, String postBody) throws MalformedURLException, IOException, ProtocolException {
		return createInputStream(getPlextURL(), requestParameter, postBody);
	}

	public static HttpURLConnection createInputStream(String url, Map<String, String> requestParameter, String postBody) throws MalformedURLException, IOException, ProtocolException {
		URL plexts = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) plexts.openConnection();
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);

		for (Entry<String, String> entry : requestParameter.entrySet()) {
			connection.setRequestProperty(entry.getKey(), entry.getValue());
		}

		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

		wr.writeBytes(postBody);
		wr.flush();
		wr.close();
		return connection;
	}

	protected String getPlextURL() {
		return "https://www.ingress.com/r/getPlexts";
	}

	private String getOutputParameters() {
		String urlParameters = "{\"minLatE6\":50100453,\"minLngE6\":8654147,\"maxLatE6\":50104664,\"maxLngE6\":8672172,\"minTimestampMs\":-1,\"maxTimestampMs\":-1,\"tab\":\"all\",\"v\":\"" + data.v
				+ "\",\"b\":\"" + data.b + "\",\"c\":\"" + data.c + "\"}";

		return urlParameters;
	}

	private Map<String, String> getRequestParameter() {
		String cookie = "csrftoken=" + data.csrftoken
				+ "; __utma=24037858.253737590.1413652003.1416056245.1416650976.48; __utmc=24037858; __utmz=24037858.1413652003.1.1.utmcsr=duckduckgo.com|utmccn=(referral)|utmcmd=referral|utmcct=/; "
				+ "SACSID=" + data.sacsid + "; " + "ingress.intelmap.lat=50.1025584721709; ingress.intelmap.lng=8.663159608840942; ingress.intelmap.zoom=17";

		Map<String, String> requestParameter = new HashMap<String, String>();
		requestParameter.put("origin", "https://www.ingress.com");
		requestParameter.put("referer", "https://www.ingress.com/intel");
		requestParameter.put("accept", "application/json, text/javascript, */*; q=0.01");
		requestParameter.put("accept-encoding", "gzip,deflate");
		requestParameter.put("accept-language", "en-US,en;q=0.8");
		requestParameter.put("x-requested-with", "XMLHttpRequest");
		requestParameter.put("x-csrftoken", data.csrftoken);
		requestParameter.put("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/37.0.2062.120 Chrome/37.0.2062.120 Safari/537.36");
		requestParameter.put("content-type", "application/json; charset=UTF-8");
		requestParameter.put("cookie", cookie);
		return requestParameter;
	}

	public void setData(RequestData data) {
		this.data = data;
	}

}
