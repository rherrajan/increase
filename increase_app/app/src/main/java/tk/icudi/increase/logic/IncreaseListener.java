package tk.icudi.increase.logic;

import java.util.List;

import tk.icudi.CaughtPlayer;
import tk.icudi.NearbyPlayer;

public interface IncreaseListener extends IncreaseLocationListener {

	public void onNearbyAgentsRefreshSuccesfull(List<NearbyPlayer> players);
	public void onNearbyAgentsRefreshFailure(Exception exception);
	public void onNearbyAgentsRefreshStart();


	public void onHackedAgentsRefreshSuccesfull(List<CaughtPlayer> hackedAgents);

}
