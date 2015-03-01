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

	IncreaseServer server = new IncreaseServer();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
	}

	public void onClickRefresh(View view) {
		
		// # Just for testing, allow network access in the main thread
		// # NEVER use this is productive code
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			List<Unit> units = server.getNearbyPlayers();
			Log.i(ListMobileActivity.class.getName(), units.toString());
			
			setListAdapter(new MobileArrayAdapter(this, units.toArray(new Unit[0])));
			
		} catch (Exception e) {
			Toast.makeText(this, "failed to get player information" + e, Toast.LENGTH_SHORT).show();
			Log.e(ListMobileActivity.class.getName(), "failed to get player information", e);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		String selectedValue = (String) getListAdapter().getItem(position);
		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
	}

}