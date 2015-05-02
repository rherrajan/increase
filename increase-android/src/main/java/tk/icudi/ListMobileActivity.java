package tk.icudi;

import java.util.List;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectView;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
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
		
		updateService.registerListener(this);
	}

	public void onClickRefresh(View view) {
		progressBar.setVisibility(View.VISIBLE); 
		//setListAdapter(null);
		updateService.updatePlayers();
	}

	public void onClickToggleUpdates(View view) {
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
		progressBar.setVisibility(View.GONE);
		
		setListAdapter(new MobileArrayAdapter(ListMobileActivity.this, players.toArray(new NearbyPlayer[players.size()])));
	}
	
	private void updateRefreshButton(Location location) {
		if(location == null){
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