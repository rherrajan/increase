package tk.icudi;

import java.util.List;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectView;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.inject.Inject;

public class ListMobileActivity extends RoboListActivity implements IncreaseListener {

	@Inject
	UpdateService updateService;

	@Inject
	AlarmService alarmService;
	
	@InjectView(R.id.button_refresh)
	Button button_refresh;

	@InjectView(R.id.toggle_updates)
	CheckBox checkBox;

	@InjectView(R.id.waiting)
	ProgressBar progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		alarmService.init();
		updateService.init();
		updateService.registerListener(this);
		
		setContentView(R.layout.activity_main);
		button_refresh.setEnabled(false);
		progressBar.setVisibility(View.GONE);
		registerForContextMenu(this.getListView());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		checkBox.setChecked(alarmService.isAutoUpdates());
		onPlayerChanged(updateService.getLastPlayers());
	}
	
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_refresh:
	    		progressBar.setVisibility(View.VISIBLE);
	    		updateService.updatePlayers();
	            return true;
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Object player_raw = getListView().getItemAtPosition(info.position);
		NearbyPlayer player = (NearbyPlayer)player_raw;
		
		switch (item.getItemId()) {
		case R.id.block_agent:		
			this.updateService.blockPlayer(player);
			onPlayerChanged(updateService.getLastPlayers());
			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}

	@Deprecated
	public void onClickRefresh(View view) {
		progressBar.setVisibility(View.VISIBLE);
		updateService.updatePlayers();
	}

	public void onClickToggleUpdates(View view) {
		if (checkBox.isChecked()) {
			alarmService.aktivateAutoUpdates(true);
		} else {
			alarmService.aktivateAutoUpdates(false);
		}
	}

	public void onLocationChanged(Location location) {
		updateRefreshButton(location);
	}

	public void onPlayerChanged(List<NearbyPlayer> players) {
		progressBar.setVisibility(View.GONE);

		setListAdapter(new MobileArrayAdapter(ListMobileActivity.this, players.toArray(new NearbyPlayer[players.size()])));
	}

	private void updateRefreshButton(Location location) {
		if (location == null) {
			button_refresh.setText("no location");
			button_refresh.setEnabled(false);
			return;
		}

		int acc = (int) location.getAccuracy();

		button_refresh.setText("Refresh (" + acc + "m acc)");

		if (acc < UpdateService.min_acc_for_disable) {
			button_refresh.setEnabled(true);
		} else {
			button_refresh.setEnabled(false);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		NearbyPlayer selectedValue = (NearbyPlayer) getListAdapter().getItem(position);
		String text = "on '" + selectedValue.getLocation() + "' " + selectedValue.getHumanReadableTime() + " ago ";
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
	
	public void onRefreshFailure(Exception exception) {
		Toast.makeText(this, "failed to get player information" + exception, Toast.LENGTH_SHORT).show();
		Log.e(ListMobileActivity.class.getName(), "failed to get player information", exception);
		progressBar.setVisibility(View.GONE);
	}

}