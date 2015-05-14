package tk.icudi;

import java.util.List;

import roboguice.fragment.RoboListFragment;
import tk.icudi.business.AlarmService;
import tk.icudi.business.IncreaseListener;
import tk.icudi.business.UpdateService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

public class ListAgentsFragment extends RoboListFragment implements IncreaseListener {

	@Inject
	private UpdateService updateService;

	@Inject
	private AlarmService alarmService;

	private Menu menu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		alarmService.init();
		updateService.init();
		updateService.registerListener(this);

		setHasOptionsMenu(true);
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.agent_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		showRefreshAnimation(false);
		registerForContextMenu(this.getListView());
	}

	@Override
	public void onResume() {
		super.onResume();

		onPlayerRefreshSuccesfull(updateService.getLastPlayers());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_activity_actions, menu);

		this.menu = menu;
		updateAccuracy(updateService.getAccuracy());

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_refresh:
			showRefreshAnimation(true);
			updateService.updatePlayers();
			return true;

		case R.id.action_settings:
			Intent i = new Intent(getActivity(), ConfigurationActivity.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showRefreshAnimation(boolean activate) {

		if (menu == null) {
			return;
		}

		MenuItem refreshItem = menu.findItem(R.id.action_refresh);

		View actionView = refreshItem.getActionView();
		
		if (activate) {

			if(actionView == null){
				refreshItem.setActionView(R.layout.iv_refresh);

				Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_refresh);
				rotation.setRepeatCount(Animation.INFINITE);
				refreshItem.getActionView().startAnimation(rotation);
			}

		} else {
			
			if (actionView != null) {
				actionView.clearAnimation();
				refreshItem.setActionView(null);
			}
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Object player_raw = getListView().getItemAtPosition(info.position);
		NearbyPlayer player = (NearbyPlayer) player_raw;

		switch (item.getItemId()) {
		case R.id.block_agent:
			this.updateService.blockPlayer(player);
			onPlayerRefreshSuccesfull(updateService.getLastPlayers());
			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}

	public void onLocationChanged(Location location) {
		final int acc;
		if (location == null) {
			acc = -1;
		} else {
			acc = (int) location.getAccuracy();
		}

		updateAccuracy(acc);
	}

	public void onPlayerRefreshStart() {
		showRefreshAnimation(true);
	}
	
	public void onPlayerRefreshSuccesfull(List<NearbyPlayer> players) {
		showRefreshAnimation(false);

		setListAdapter(new MobileArrayAdapter(getActivity(), players.toArray(new NearbyPlayer[players.size()])));
	}

	public void onPlayerRefreshFailure(Exception exception) {
		Toast.makeText(getActivity(), "failed to get player information" + exception, Toast.LENGTH_SHORT).show();
		Log.e(ListAgentsFragment.class.getName(), "failed to get player information", exception);
		showRefreshAnimation(false);
	}
	
	private void updateAccuracy(int acc) {

		if (menu == null) {
			return;
		}

		final String title;
		if (acc == -1) {
			title = getResources().getString(R.string.action_acc_default);
		} else {
			title = acc + "m";
		}

		menu.findItem(R.id.action_acc).setTitle(title);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		NearbyPlayer selectedValue = (NearbyPlayer) getListAdapter().getItem(position);
		String text = "on '" + selectedValue.getLocation() + "' " + selectedValue.getHumanReadableTime() + " ago ";
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}


}