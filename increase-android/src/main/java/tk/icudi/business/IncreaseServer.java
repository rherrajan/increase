package tk.icudi.business;

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

import tk.icudi.CaughtPlayer;
import tk.icudi.NearbyPlayer;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class IncreaseServer {

	private static final String baseURL = "http://sylvan-dragon-772.appspot.com";

	public List<NearbyPlayer> getNearbyPlayers(Location userLocation) throws ClientProtocolException, IOException {

		int lat = locDoubleToInt(userLocation.getLatitude());
		int lng = locDoubleToInt(userLocation.getLongitude());

		String jsonString = getJsonString("/player/nearby?lat=" + lat + "&lng=" + lng);
		return new Gson().<List<NearbyPlayer>> fromJson(jsonString, new TypeToken<List<NearbyPlayer>>() {
		}.getType());
	}

	int locDoubleToInt(double doubleLoc) {
		return (int) (doubleLoc * 10000000);
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

		Log.i(IncreaseServer.class.getName(), "call url: " + url);

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

	public boolean addNearbyPlayer(CaughtPlayer addPlayerInput) throws ClientProtocolException, IOException {
		
		String jsonString = getJsonString("/player/add?" + addPlayerInput.makeQueryString());
		return jsonString.contains("success");
	}

}
