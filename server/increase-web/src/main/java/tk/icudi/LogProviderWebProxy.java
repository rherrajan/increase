package tk.icudi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LogProviderWebProxy extends RequestDataProvider implements LogProvider {

	public LogProviderWebProxy(RequestData data) {
		super(data);
	}

	@Override
	public InputStream provideLogs() throws IOException {

		String plextUrl = "http://lienz.lima.zone/plext.php?version=" + data.v + "&csrftoken=" + data.csrftoken + "&sacsid=" + data.sacsid;

		System.out.println(" --- plextUrl: " + plextUrl);

		HttpURLConnection connection = (HttpURLConnection) new URL(plextUrl).openConnection();
		return connection.getInputStream();
	}

}
