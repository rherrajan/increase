package tk.icudi;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class LogProviderWeb implements LogProvider {

	private String csrftoken;
	private String cookie;
	private String v;
	private String b;
	private String c;

	public void setCsrftoken(String csrftoken) {
		this.csrftoken = csrftoken;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public void setV(String v) {
		this.v = v;
	}

	public void setB(String b) {
		this.b = b;
	}

	public void setC(String c) {
		this.c = c;
	}

	private void assertInputComplete() {
		if (csrftoken == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: csrftoken");
		}

		if (cookie == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: cookie");
		}

		if (v == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: v");
		}

		if (b == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: b");
		}

		if (c == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: c");
		}

	}

	@Override
	public InputStream provideLogs() throws IOException {

		assertInputComplete();

		URL plexts = new URL("https://www.ingress.com/r/getPlexts");
		HttpURLConnection myURLConnection = (HttpURLConnection) plexts.openConnection();
		myURLConnection.setRequestMethod("POST");
		myURLConnection.setUseCaches(false);
		myURLConnection.setDoInput(true);
		myURLConnection.setDoOutput(true);

		myURLConnection.setRequestProperty("origin", "https://www.ingress.com");
		myURLConnection.setRequestProperty("referer", "https://www.ingress.com/intel");
		myURLConnection.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
		myURLConnection.setRequestProperty("accept-encoding", "gzip,deflate");
		myURLConnection.setRequestProperty("accept-language", "en-US,en;q=0.8");
		myURLConnection.setRequestProperty("x-requested-with", "XMLHttpRequest");
		myURLConnection.setRequestProperty("x-csrftoken", csrftoken);
		myURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/37.0.2062.120 Chrome/37.0.2062.120 Safari/537.36");
		myURLConnection.setRequestProperty("content-type", "application/json; charset=UTF-8");
		myURLConnection.setRequestProperty("cookie", cookie);

		DataOutputStream wr = new DataOutputStream(myURLConnection.getOutputStream());

		String urlParameters = "{\"minLatE6\":50100453,\"minLngE6\":8654147,\"maxLatE6\":50104664,\"maxLngE6\":8672172,\"minTimestampMs\":-1,\"maxTimestampMs\":-1,\"tab\":\"all\",\"v\":\"" + v
				+ "\",\"b\":\"" + b + "\",\"c\":\"" + c + "\"}";
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		return new GZIPInputStream(myURLConnection.getInputStream());
	}

}
