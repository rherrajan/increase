package tk.icudi.view;

import java.util.List;

import roboguice.fragment.RoboListFragment;
import tk.icudi.NearbyPlayer;
import tk.icudi.R;
import tk.icudi.business.IncreaseListener;
import tk.icudi.business.UpdateService;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

public class ListAgentsFragment extends RoboListFragment implements IncreaseListener {

	@Inject
	private UpdateService updateService;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.agent_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		registerForContextMenu(this.getListView());
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
		case R.id.agent_block:
			this.updateService.blockPlayer(player);
			onPlayerRefreshSuccesfull(updateService.getLastPlayers());
			return true;

		case R.id.agent_add:
			this.updateService.addPlayer(player);
			onPlayerRefreshSuccesfull(updateService.getLastPlayers());
			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		NearbyPlayer selectedValue = (NearbyPlayer) getListAdapter().getItem(position);
		String text = "on '" + selectedValue.getLocation() + "' " + selectedValue.getHumanReadableTime() + " ago ";
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}
	
	public void onPlayerRefreshSuccesfull(List<NearbyPlayer> players) {
		setListAdapter(new AgentlistAdapter(getActivity(), players.toArray(new NearbyPlayer[players.size()])));
	}

	public void onPlayerRefreshFailure(Exception exception) {
		Toast.makeText(getActivity(), "failed to get player information" + exception, Toast.LENGTH_SHORT).show();
		Log.e(ListAgentsFragment.class.getName(), "failed to get player information", exception);
	}

	public void onFirstLocation() {
		
	}
	
	public void onLocationChanged(Location location) {

	}

	public void onPlayerRefreshStart() {

	}

}