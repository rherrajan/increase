package tk.icudi.business;

import java.util.List;

import tk.icudi.increase.CaughtPlayer;
import android.location.Location;

public interface IncreaseLocationListener {

	public void onLocationChanged(Location location);

	public void onFirstLocation();

	void onHackedAgentsRefreshSuccesfull(List<CaughtPlayer> agents);
}
