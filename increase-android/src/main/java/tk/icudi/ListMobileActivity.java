package tk.icudi;

import java.util.List;

import roboguice.activity.RoboListActivity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

public class ListMobileActivity extends RoboListActivity implements IncreaseListener {

	@Inject
	UpdateService updateService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.button_refresh);
		button.setEnabled(false);
		
		CheckBox checkBox = (CheckBox) findViewById(R.id.toggle_updates);
		checkBox.setChecked(true);
		
		updateService.registerListener(this);
		updateService.updatePlayers();
	}

	public void onClickRefresh(View view) {
		setListAdapter(null);
		updateService.updatePlayers();
	}

	public void onClickToggleUpdates(View view) {
		CheckBox checkBox = (CheckBox) findViewById(R.id.toggle_updates);
		if(checkBox.isChecked()){
			updateService.setAutoUpdates(true);
		} else {
			updateService.setAutoUpdates(false);
		}
	}
	
	public void onLocationChanged(Location location) {
		updateRefreshButton(location);
	}
	
	public void onPlayerChanged(List<NearbyPlayer> players) {
		setListAdapter(new MobileArrayAdapter(ListMobileActivity.this, players.toArray(new NearbyPlayer[players.size()])));
	}
	
	private void updateRefreshButton(Location location) {
		if(location == null){
			return;
		}
		
		int acc = (int) location.getAccuracy();
		
		Button button = (Button) findViewById(R.id.button_refresh);
		button.setText("Refresh (" + acc + "m acc)");

		if (acc < 2000) {
			button.setEnabled(true);
		} else {
			button.setEnabled(false);
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		NearbyPlayer selectedValue = (NearbyPlayer) getListAdapter().getItem(position);
		String text = "on '" + selectedValue.getLocation() + "' " + selectedValue.getHumanReadableTime() + " ago ";
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

}