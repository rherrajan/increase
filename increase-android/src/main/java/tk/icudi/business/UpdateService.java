package tk.icudi.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tk.icudi.ListMobileActivity;
import tk.icudi.NearbyPlayer;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UpdateService {

	public static final int max_ranking_for_vibration = 300;
	public static final int max_ranking_for_bold_display = 1000;
	public static final int max_ranking_for_notification = 5000;

	@Inject
	Context context;

	@Inject
	LocationManager locationManager;

	@Inject
	NotificationService notificationService;

	Set<IncreaseListener> listener = new HashSet<IncreaseListener>();

	private boolean isInitialised = false;
	private Location userLocation;

	private List<NearbyPlayer> lastPlayers = new ArrayList<NearbyPlayer>();
	private Set<NearbyPlayer> blockedPlayers = new HashSet<NearbyPlayer>();

	public void registerListener(IncreaseListener increaseListener) {
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

				boolean firstCall = UpdateService.this.userLocation == null;

				UpdateService.this.userLocation = location;
				for (IncreaseListener increaseListener : listener) {
					increaseListener.onLocationChanged(location);
				}

				if (firstCall) {
					for (IncreaseListener increaseListener : listener) {
						increaseListener.onPlayerRefreshStart();
					}
					
					UpdateService.this.updatePlayers();
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

	public static abstract class GetNearbyPlayersTask extends AsyncTask<Location, Integer, List<NearbyPlayer>> {

		private IncreaseServer server = new IncreaseServer();
		private Exception exception;

		@Override
		protected List<NearbyPlayer> doInBackground(Location... params) {
			try {
				List<NearbyPlayer> players = server.getNearbyPlayers(params[0]);
				Log.i(ListMobileActivity.class.getName(), "found " + players.size() + " players");
				return players;

			} catch (Exception e) {
				this.exception = e;
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<NearbyPlayer> players) {
			if (players != null) {
				onSuccessfullExecute(players);
			} else {
				onFailure(exception);
			}
		}

		protected abstract void onFailure(Exception exception);

		protected abstract Context getInitiator();

		protected abstract void onSuccessfullExecute(List<NearbyPlayer> players);

	}

	public void updatePlayers() {

		if (userLocation == null) {
			Toast.makeText(context, "no location", Toast.LENGTH_SHORT).show();
			return;
		}

		for (IncreaseListener increaseListener : listener) {
			increaseListener.onPlayerRefreshStart();
		}
		
		new GetNearbyPlayersTask() {

			protected void onSuccessfullExecute(List<NearbyPlayer> players) {

				lastPlayers = removeBlockedPlayers(players);

				for (IncreaseListener increaseListener : listener) {
					increaseListener.onPlayerRefreshSuccesfull(lastPlayers);
				}

				if (lastPlayers.isEmpty()) {
					return;
				}

				notificationService.nearestPlayer(lastPlayers.get(0));
			}

			protected void onFailure(Exception exception) {
				
				for (IncreaseListener increaseListener : listener) {
					increaseListener.onPlayerRefreshFailure(exception);
				}
				
			}
			
			@Override
			protected Context getInitiator() {
				return context;
			}
			
		}.execute(userLocation);
	}
	

	public List<NearbyPlayer> getLastPlayers() {
		return lastPlayers;
	}

	public void blockPlayer(NearbyPlayer player) {
		blockedPlayers.add(player);

		Toast.makeText(context, "blocked Player '" + player.getName() + "'", Toast.LENGTH_SHORT).show();

		lastPlayers = removeBlockedPlayers(lastPlayers);
	}

	private List<NearbyPlayer> removeBlockedPlayers(List<NearbyPlayer> players) {

		List<NearbyPlayer> result = new ArrayList<NearbyPlayer>();

		for (NearbyPlayer nearbyPlayer : players) {

			if (blockedPlayers.contains(nearbyPlayer) == false) {
				result.add(nearbyPlayer);
			}
		}

		return result;
	}

	public int getAccuracy() {
		if(userLocation == null){
			return -1;
		} else {
			return (int)userLocation.getAccuracy();
		}
	}

}
