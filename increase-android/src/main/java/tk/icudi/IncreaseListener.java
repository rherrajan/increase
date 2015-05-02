package tk.icudi;

import java.util.List;

import android.location.Location;

public interface IncreaseListener {

	public void onLocationChanged(Location location);
	
	public void onPlayerChanged(List<NearbyPlayer> players);

	public void onRefreshFailure(Exception exception);
}
