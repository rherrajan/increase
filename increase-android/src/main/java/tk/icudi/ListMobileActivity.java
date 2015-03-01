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

import android.app.ListActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class ListMobileActivity extends ListActivity {

	static final String[] MOBILE_OS = new String[] { "Android", "iOS", "WindowsMobile", "Blackberry" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new MobileArrayAdapter(this, MOBILE_OS));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		// get selected items
		String selectedValue = (String) getListAdapter().getItem(position);
		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

		logNearbyPlayers();

	}

	private void logNearbyPlayers() {
		// # Just for testing, allow network access in the main thread
		// # NEVER use this is productive code
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		String input = readNearbyPlayers();
		try {
			@SuppressWarnings("unchecked")
			List<Unit> units = new Gson().fromJson(input, List.class);
			
			Log.i(ListMobileActivity.class.getName(), units.toString());
		} catch (Exception e) {
			Log.e(ListMobileActivity.class.getName(), "Failed to parse json", e);
		}
	}

	public String readNearbyPlayers() {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://sylvan-dragon-772.appspot.com/player/nearby?lat=50586690&lng=8679832");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(ListMobileActivity.class.getName(), "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

}