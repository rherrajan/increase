package tk.icudi.increase;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import tk.icudi.LogProviderWeb;
import tk.icudi.RequestData;

public class LogProviderGAEWeb extends LogProviderWeb {

	public LogProviderGAEWeb(RequestData data) {
		super(data);
//		this.crawler = new Crawler();
	}

	@Override
	protected InputStream provideLogs(String plextURL, Map<String, String> requestParameter, String postBody) throws MalformedURLException, IOException, ProtocolException {

		// TODO: Make able for sandbox use
		
		URL plexts = new URL(plextURL);
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

		return new GZIPInputStream(connection.getInputStream());
	}
}
