package tk.icudi.business;

import java.util.HashSet;
import java.util.Set;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
class LocationService {

	@Inject
	LocationManager locationManager;
	
	private boolean isInitialised = false;
	private Location userLocation;
	
	private Set<IncreaseLocationListener> listener = new HashSet<IncreaseLocationListener>();
	
	public void registerListener(IncreaseLocationListener increaseListener) {
		listener.add(increaseListener);

		if (isEmulator()) {
			increaseListener.onLocationChanged(userLocation);
		}
	}
	
	public synchronized void init() {
		
		if (isInitialised) {
			return;
		}
		isInitialised = true;
		
		if (isEmulator() == false) {
			setUpLocationService();
		} else {
			userLocation = createDummyLocation();
		}		
	}
	
	private void setUpLocationService() {

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {

			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}

			public void onLocationChanged(Location location) {

				boolean firstCall = LocationService.this.userLocation == null;

				if(location == null){
					long duration = System.currentTimeMillis() - userLocation.getTime();
					if(duration > 1000 * 30){
						// Better old than no location
						return;
					} else {
						// Too old: better no location
					}
				}
				
				updateLocation(location, firstCall);
			}

			private void updateLocation(Location location, boolean firstCall) {
				LocationService.this.userLocation = location;
				for (IncreaseLocationListener locationListener : listener) {
					locationListener.onLocationChanged(location);
				}

				if (firstCall) {
					for (IncreaseLocationListener locationListener : listener) {
						locationListener.onFirstLocation();
					}
				}
			}
		};

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}
	
	private Location createDummyLocation() {
		Location dummyLoc = new Location("dummyprovider");
		dummyLoc.setLatitude(50.107356);
		dummyLoc.setLongitude(8.664123);
		dummyLoc.setAccuracy(815);

		return dummyLoc;
	}

	private boolean isEmulator() {
		return locationManager.getProvider(LocationManager.NETWORK_PROVIDER) == null;
	}
	
	public int getAccuracy() {
		if(userLocation == null){
			return -1;
		} else {
			return (int)userLocation.getAccuracy();
		}
	}

	Location getUserLocation() {
		return userLocation;
	}
	
}
