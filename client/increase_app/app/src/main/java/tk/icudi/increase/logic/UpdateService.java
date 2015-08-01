package tk.icudi.increase.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tk.icudi.CaughtPlayer;
import tk.icudi.NearbyPlayer;

import android.content.Context;
import android.location.Location;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;

public class UpdateService implements IncreaseLocationListener {

	private Context context;
	private NotificationService notificationService;
	private LocationService locationService;
	private AlarmService alarmService;

	private Set<IncreaseListener> listener = new HashSet<IncreaseListener>();
	private List<NearbyPlayer> lastPlayers = new ArrayList<NearbyPlayer>();
	private Set<NearbyPlayer> blockedPlayers = new HashSet<NearbyPlayer>();

	private boolean isUpdating = false;

	public UpdateService(Context context){
		this.notificationService = new NotificationService(context);
		this.locationService = new LocationService(context);
		this.alarmService = new AlarmService(context);
		this.context = context;

		notificationService.setActionToNotificate(context.getClass());
	}

	public void init() {
		locationService.init();
		locationService.registerListener(this);
		alarmService.init();
		notificationService.init();
	}
	
	public void registerListener(IncreaseListener increaseListener) {
		listener.add(increaseListener);
		locationService.registerListener(increaseListener);
	}

	public void updateNotification() {
		if (lastPlayers.isEmpty() == false) {
			notificationService.nearestPlayer(lastPlayers.get(0));
		}
	}
	
	public void updateHackedPlayers() {
		
		String device_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID); 
		
		new GetHackedAgentsTask() {

			protected void onSuccessfullExecute(List<CaughtPlayer> players) {

				for (IncreaseListener increaseListener : listener) {
					increaseListener.onHackedAgentsRefreshSuccesfull(players);
				}
			}

			@Override
			protected void onFailure(Exception exception) {
				Toast.makeText(context, "failure retrieving hacked agents", Toast.LENGTH_SHORT).show();
			}


		}.execute(device_id);
		
	}
	
	public void updateNearbyAgents() {

		final Location userLocation = locationService.getUserLocation();
		if (userLocation == null) {
			return;
		}

		if(acquireLock() == false){
			Log.d(this.getClass().getSimpleName(), "already updating");
			return;
		}
		
		for (IncreaseListener increaseListener : listener) {
			increaseListener.onNearbyAgentsRefreshStart();
		}

		new GetNearbyPlayersTask() {

			protected void onSuccessfullExecute(List<NearbyPlayer> players) {

				releaseLock();
				
				lastPlayers = removeBlockedPlayers(players);

				for (IncreaseListener increaseListener : listener) {
					increaseListener.onNearbyAgentsRefreshSuccesfull(lastPlayers);
				}

				updateNotification();

			}


			protected void onFailure(Exception exception) {

				releaseLock();
				
				for (IncreaseListener increaseListener : listener) {
					increaseListener.onNearbyAgentsRefreshFailure(exception);
				}

			}


		}.execute(userLocation);
	}

	private synchronized boolean acquireLock() {
		
		boolean succes = isUpdating == false;
		
		isUpdating = true;
		
		return succes;
	}

	private synchronized void releaseLock() {
		isUpdating = false;
	}
	
	public List<NearbyPlayer> getLastPlayers() {
		return lastPlayers;
	}

	public void blockPlayer(NearbyPlayer player) {
		blockedPlayers.add(player);

		lastPlayers = removeBlockedPlayers(lastPlayers);

		Toast.makeText(context, "blocked Player '" + player.getName() + "'", Toast.LENGTH_SHORT).show();
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

	public void addPlayer(NearbyPlayer player) {

		final CaughtPlayer input = new CaughtPlayer();
		input.playername = player.getName();
		input.accuracy = locationService.getAccuracy();
		input.device_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID); 
		input.distance = player.getDistance();
		input.timestamp = player.getTimestamp();
		
		new AddNearbyPlayersTask() {
			@Override
			protected void onPostExecute(Boolean success) {
				if (success != null && success.equals(Boolean.TRUE)) {
					Toast.makeText(context, "add: " + input.playername, Toast.LENGTH_SHORT).show();
				} else if (exception != null) {
					Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show();
					exception.printStackTrace();
				} else {
					Toast.makeText(context, "server did not allow operation", Toast.LENGTH_SHORT).show();
				}
			}

		}.execute(input);

	}

	public void onLocationChanged(Location location) {
	}

	public void onFirstLocation() {
		updateNearbyAgents();
	}

	public int getAccuracy() {
		return locationService.getAccuracy();
	}

	public void onHackedAgentsRefreshSuccesfull(List<CaughtPlayer> agents) {

	}

}
