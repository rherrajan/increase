package tk.icudi;

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
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UpdateService {

	private static final int max_ranking_for_notification = 500;
	public static final int min_acc_for_disable = 2000;
	private static final int seconds_till_refresh = 60;
	
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

		sheduleUpdate(seconds_till_refresh);
	}

	private void sheduleUpdate(int sec) {
		handler.postDelayed(new Runnable() {
			public void run() {
				updatePlayers();
				if(doAutoUpdates){
					sheduleUpdate(seconds_till_refresh);
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
				UpdateService.this.userLocation = location;
				for (IncreaseListener increaseListener : listener) {
					increaseListener.onLocationChanged(location);
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

		// # Just for testing, allow network access in the main thread
		// # NEVER use this is productive code
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		if (userLocation == null) {
			Toast.makeText(context, "no location", Toast.LENGTH_SHORT).show();
			return;
		}

		new GetNearbyPlayersTask() {
			protected void onSuccessfullExecute(List<NearbyPlayer> players) {
				for (IncreaseListener increaseListener : listener) {
					increaseListener.onPlayerChanged(players);
				}
				
				sendNotifcation(players);
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

}
