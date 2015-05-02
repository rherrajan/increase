package tk.icudi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UpdateService {

	public static final int max_ranking_for_bold_display = 1000;
	public static final int max_ranking_for_notification = 5000;
	public static final int min_acc_for_disable = 2000;
	public static final int seconds_till_player_refresh = 60;
	
	@Inject
	LocationManager locationManager;

	@Inject
	Context context;

	@Inject
	NotificationService notificationService;
	
	Set<IncreaseListener> listener = new HashSet<IncreaseListener>();

	private Handler handler = new Handler();
	private boolean isInitialised = false;
	private Location userLocation;
	private boolean doAutoUpdates = true;
	
	private List<NearbyPlayer> lastPlayers = new ArrayList<NearbyPlayer>();
	private Set<NearbyPlayer> blockedPlayers = new HashSet<NearbyPlayer>();
	
	public void registerListener(IncreaseListener increaseListener) {
		listener.add(increaseListener);

		if (isInitialised == false) {
			init();
			isInitialised = true;
		}

		if (isEmulator()) {
			increaseListener.onLocationChanged(userLocation);
		}
	}

	private void init() {
		if (isEmulator() == false) {
			setUpLocationService();
		} else {
			userLocation = createDummyLocation();
		}

		sheduleUpdate(seconds_till_player_refresh);
	}

	private void sheduleUpdate(int sec) {
		handler.postDelayed(new Runnable() {
			public void run() {
				updatePlayers();
				if(doAutoUpdates){
					sheduleUpdate(seconds_till_player_refresh);
				}
			}
		}, 1000*sec);
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
				
				if(firstCall){
					UpdateService.this.updatePlayers();
				}
			}
		};

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
				Toast.makeText(getInitiator(), "failed to get player information" + exception, Toast.LENGTH_SHORT).show();
				Log.e(ListMobileActivity.class.getName(), "failed to get player information", exception);
			}
		}

		protected abstract Context getInitiator();

		protected abstract void onSuccessfullExecute(List<NearbyPlayer> players);

	}

	public void updatePlayers() {

		if (userLocation == null) {
			Toast.makeText(context, "no location", Toast.LENGTH_SHORT).show();
			return;
		}

		new GetNearbyPlayersTask() {
			
			protected void onSuccessfullExecute(List<NearbyPlayer> players) {
				
				lastPlayers = removeBlockedPlayers(players);
				
				for (IncreaseListener increaseListener : listener) {
					increaseListener.onPlayerChanged(lastPlayers);
				}
				
				sendNotifcation(lastPlayers);
			}

			private List<NearbyPlayer> removeBlockedPlayers(List<NearbyPlayer> players) {
				
				List<NearbyPlayer> result = new ArrayList<NearbyPlayer>();
				
				for (NearbyPlayer nearbyPlayer : players) {

					if(blockedPlayers.contains(nearbyPlayer)){
						System.out.println(" --- blocked: " + nearbyPlayer);
						
					} else {
						result.add(nearbyPlayer);
					}
				}
				
				return result;
			}

			@Override
			protected Context getInitiator() {
				return context;
			}
		}.execute(userLocation);
	}

	private void sendNotifcation(List<NearbyPlayer> players) {
		
		if(players.isEmpty()){
			return;
		}
		
		if(players.get(0).getRank() < max_ranking_for_notification ){
			notificationService.sendNotification(players.get(0));
		}

	}
	
	public void setAutoUpdates(boolean doAutoUpdates) {
		this.doAutoUpdates = doAutoUpdates;
		if(doAutoUpdates){
			sheduleUpdate(0);
		}
	}

	public boolean isAutoUpdates() {
		return doAutoUpdates;
	}

	public List<NearbyPlayer> getLastPlayers() {
		return lastPlayers;
	}

	public void blockPlayer(NearbyPlayer player) {
		blockedPlayers.add(player);
		
		Toast.makeText(context, "blocked Player '" + player.getName() + "'", Toast.LENGTH_SHORT).show();		
	}

}
