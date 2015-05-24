package tk.icudi.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tk.icudi.CaughtPlayer;
import tk.icudi.NearbyPlayer;
import android.content.Context;
import android.location.Location;
import android.provider.Settings.Secure;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UpdateService implements IncreaseLocationListener {

	@Inject
	private Context context;

	@Inject
	private NotificationService notificationService;

	@Inject
	private LocationService locationService;

	@Inject
	private AlarmService alarmService;

	private Set<IncreaseListener> listener = new HashSet<IncreaseListener>();
	private List<NearbyPlayer> lastPlayers = new ArrayList<NearbyPlayer>();
	private Set<NearbyPlayer> blockedPlayers = new HashSet<NearbyPlayer>();

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
		List<NearbyPlayer> hackedAgents = new ArrayList<NearbyPlayer>();
		
		NearbyPlayer dummy = new NearbyPlayer();
		dummy.setName("dummy");
		hackedAgents.add(dummy);
		
		for (IncreaseListener increaseListener : listener) {
			increaseListener.onHackedAgentsRefreshSuccesfull(hackedAgents);
		}
		
	}
	
	public void updatePlayers() {

		final Location userLocation = locationService.getUserLocation();

		if (userLocation == null) {
			return;
		}

		for (IncreaseListener increaseListener : listener) {
			increaseListener.onNearbyAgentsRefreshStart();
		}

		new GetNearbyPlayersTask() {

			protected void onSuccessfullExecute(List<NearbyPlayer> players) {

				lastPlayers = removeBlockedPlayers(players);

				for (IncreaseListener increaseListener : listener) {
					increaseListener.onNearbyAgentsRefreshSuccesfull(lastPlayers);
				}

				updateNotification();

			}


			protected void onFailure(Exception exception) {

				for (IncreaseListener increaseListener : listener) {
					increaseListener.onNearbyAgentsRefreshFailure(exception);
				}

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

	public void addPlayer(NearbyPlayer player) {

		final CaughtPlayer input = new CaughtPlayer();
		input.playername = player.getName();
		input.accuracy = locationService.getAccuracy();
		input.device_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID); 

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
		updatePlayers();
	}

	public int getAccuracy() {
		return locationService.getAccuracy();
	}

}
