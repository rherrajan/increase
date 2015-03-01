package tk.icudi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class IncreaseServer {

	private static final String baseURL = "http://sylvan-dragon-772.appspot.com";

	public List<Unit> getNearbyPlayers() throws ClientProtocolException, IOException {
		String jsonString = getJsonString("/player/nearby?lat=50586690&lng=8679832");
		return new Gson().<List<Unit>>fromJson(jsonString, new TypeToken<List<Unit>>(){}.getType());
	}

	private String getJsonString(String string) throws ClientProtocolException, IOException {
		HttpResponse response = callURL(baseURL + string);
		return response2String(response);
	}

	private String response2String(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		InputStream content = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}

		return builder.toString();
	}

	private HttpResponse callURL(String url) throws IOException, ClientProtocolException {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		HttpResponse response = client.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode != 200) {
			throw new IOException("got response code '" + statusCode + "' from url: " + url);
		}
		return response;
	}

}
