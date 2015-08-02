package tk.icudi.view;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboListFragment;
import tk.icudi.R;
import tk.icudi.business.IncreaseListener;
import tk.icudi.business.UpdateService;
import tk.icudi.increase.CaughtPlayer;
import tk.icudi.increase.NearbyPlayer;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.inject.Inject;

public class ListHackedAgentsFragment extends RoboListFragment implements IncreaseListener{

	@Inject
	private UpdateService updateService;
	private Agentlist<CaughtPlayer> list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.agent_list, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		list = new AgentlistHacked(getActivity(), new ArrayList<CaughtPlayer>());
		setListAdapter(list);
		
		updateService.updateHackedPlayers();
	}

	public void onHackedAgentsRefreshSuccesfull(List<CaughtPlayer> agents) {
		if(list != null){
			list.clear();
			list.addAll(agents);
		}
	}
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		/*
		final CaughtPlayer selectedValue = list.getItem(position);

		PlayerHuman local = new PlayerHuman();
		PlayerAI remote = new PlayerAI(1);

		StartOptions options = new StartOptions();
		options.setReleaseCreepAtStart(false);
		options.addPlayer(local);
		options.addPlayer(remote);
		startGame(options);
		*/
	}
	
	/*
	private void startGame(StartOptions options) {
		Log.d(" --- ", " Game started: " + options);
	    Intent intent = new Intent(getActivity(), DurandalCoreActivity.class);
	    intent.putExtra(StartOptions.START_OPTIONS_MESSAGE, options);
	    
	    startActivityForResult(intent, 0);
	}
	*/
	
	public void onLocationChanged(Location location) {
		
	}

	public void onFirstLocation() {
		
	}

	public void onNearbyAgentsRefreshSuccesfull(List<NearbyPlayer> players) {
		
	}

	public void onNearbyAgentsRefreshFailure(Exception exception) {
		
	}

	public void onNearbyAgentsRefreshStart() {
		
	}




	
}
