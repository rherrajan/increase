package tk.icudi.increase.nearby;

import java.util.List;

import tk.icudi.increase.CaughtPlayer;
import tk.icudi.increase.NearbyPlayer;

public interface IncreaseListener extends IncreaseLocationListener {

	public void onNearbyAgentsRefreshSuccesfull(List<NearbyPlayer> players);
	public void onNearbyAgentsRefreshFailure(Exception exception);
	public void onNearbyAgentsRefreshStart();


	public void onHackedAgentsRefreshSuccesfull(List<CaughtPlayer> hackedAgents);

}
