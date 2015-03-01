package tk.icudi;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ListMobileActivity extends ListActivity {

	static final String[] MOBILE_OS = new String[] { "Android", "iOS", "WindowsMobile", "Blackberry" };

	IncreaseServer server = new IncreaseServer();
	
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

		try {
			List<Unit> units = server.getNearbyPlayers();
			
			Log.i(ListMobileActivity.class.getName(), units.toString());
		} catch (Exception e) {
			Log.e(ListMobileActivity.class.getName(), "Failed to parse json", e);
		}
	}

}