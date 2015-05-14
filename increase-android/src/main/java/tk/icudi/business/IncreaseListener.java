package tk.icudi.business;

import java.util.List;

import tk.icudi.NearbyPlayer;
import android.location.Location;

public interface IncreaseListener {

	public void onLocationChanged(Location location);
	
	public void onPlayerRefreshSuccesfull(List<NearbyPlayer> players);

	public void onPlayerRefreshFailure(Exception exception);

	public void onPlayerRefreshStart();
}
