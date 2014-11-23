package tk.icudi;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class LogProviderWeb implements LogProvider {

	private RequestData data;

	private void assertInputComplete() {

		if (data == null) {
			throw new IllegalArgumentException("All Input-Parameters are missing");
		}

		if (data.csrftoken == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: csrftoken");
		}

		if (data.sacsid == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: cookie");
		}

		if (data.v == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: v");
		}

		// if (b == null) {
		// throw new IllegalArgumentException("Input-Parameter is missing: b");
		// }

		// if (c == null) {
		// throw new IllegalArgumentException("Input-Parameter is missing: c");
		// }

	}

	@Override
	public InputStream provideLogs() throws IOException {

		assertInputComplete();

		String cookie = "csrftoken=" + data.csrftoken
				+ "; __utma=24037858.253737590.1413652003.1416056245.1416650976.48; __utmc=24037858; __utmz=24037858.1413652003.1.1.utmcsr=duckduckgo.com|utmccn=(referral)|utmcmd=referral|utmcct=/; "
				+ "SACSID=" + data.sacsid + "; " + "ingress.intelmap.lat=50.1025584721709; ingress.intelmap.lng=8.663159608840942; ingress.intelmap.zoom=17";

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
		myURLConnection.setRequestProperty("x-csrftoken", data.csrftoken);
		myURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/37.0.2062.120 Chrome/37.0.2062.120 Safari/537.36");
		myURLConnection.setRequestProperty("content-type", "application/json; charset=UTF-8");
		myURLConnection.setRequestProperty("cookie", cookie);

		DataOutputStream wr = new DataOutputStream(myURLConnection.getOutputStream());

		String urlParameters = "{\"minLatE6\":50100453,\"minLngE6\":8654147,\"maxLatE6\":50104664,\"maxLngE6\":8672172,\"minTimestampMs\":-1,\"maxTimestampMs\":-1,\"tab\":\"all\",\"v\":\"" + data.v
				+ "\",\"b\":\"" + data.b + "\",\"c\":\"" + data.c + "\"}";
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		return new GZIPInputStream(myURLConnection.getInputStream());
	}

	public void setData(RequestData data) {
		this.data = data;
	}

}
