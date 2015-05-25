package tk.icudi.view;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboListFragment;
import tk.icudi.CaughtPlayer;
import tk.icudi.NearbyPlayer;
import tk.icudi.R;
import tk.icudi.business.IncreaseListener;
import tk.icudi.business.UpdateService;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
