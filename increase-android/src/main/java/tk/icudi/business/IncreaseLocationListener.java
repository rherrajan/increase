package tk.icudi.business;

import android.location.Location;

public interface IncreaseLocationListener {

	public void onLocationChanged(Location location);

	public void onFirstLocation();
}
