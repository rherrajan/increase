package tk.icudi;

import java.util.List;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectView;
import android.location.Location;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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

	@InjectView(R.id.button_refresh)
	Button button_refresh;

	@InjectView(R.id.toggle_updates)
	CheckBox checkBox;

	@InjectView(R.id.waiting)
	ProgressBar progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		button_refresh.setEnabled(false);
		checkBox.setChecked(updateService.isAutoUpdates());
		progressBar.setVisibility(View.GONE);
		onPlayerChanged(updateService.getLastPlayers());

		registerForContextMenu(this.getListView());
		updateService.registerListener(this);
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
			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}

	public void onClickRefresh(View view) {
		progressBar.setVisibility(View.VISIBLE);
		// setListAdapter(null);
		updateService.updatePlayers();
	}

	public void onClickToggleUpdates(View view) {
		if (checkBox.isChecked()) {
			updateService.setAutoUpdates(true);
		} else {
			updateService.setAutoUpdates(false);
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

}