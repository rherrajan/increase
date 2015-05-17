package tk.icudi.business;

import java.util.List;

import tk.icudi.NearbyPlayer;

public interface IncreaseListener extends IncreaseLocationListener {

	public void onPlayerRefreshSuccesfull(List<NearbyPlayer> players);

	public void onPlayerRefreshFailure(Exception exception);

	public void onPlayerRefreshStart();

}
