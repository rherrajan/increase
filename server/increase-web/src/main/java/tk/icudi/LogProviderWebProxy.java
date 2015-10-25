package tk.icudi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import tk.icudi.increase.Point;

public class LogProviderWebProxy extends RequestDataProvider implements LogProvider {

	private Point userLoc;

	public LogProviderWebProxy(RequestData data, Point userLoc) {
		super(data);
		this.userLoc = userLoc;
	}

	@Override
	public InputStream provideLogs() throws IOException {

		String plextUrl = "http://lienz.lima.zone/plext2.php?lat=" + userLoc.getLat() + "&lng=" + userLoc.getLng() + "&version=" + data.v + "&csrftoken=" + data.csrftoken + "&sacsid=" + data.sacsid;

		HttpURLConnection connection = (HttpURLConnection) new URL(plextUrl).openConnection();
		return connection.getInputStream();
	}

}
