package tk.icudi.increase.logic;

import java.util.List;

import tk.icudi.CaughtPlayer;
import android.location.Location;

public interface IncreaseLocationListener {

	public void onLocationChanged(Location location);

	public void onFirstLocation();

	void onHackedAgentsRefreshSuccesfull(List<CaughtPlayer> agents);
}
