package tk.icudi.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tk.icudi.NearbyPlayer;
import tk.icudi.view.ListMobileActivity;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UpdateService implements IncreaseLocationListener {

	public static final int max_ranking_for_vibration = 300;
	public static final int max_ranking_for_bold_display = 1000;
	public static final int max_ranking_for_notification = 5000;

	@Inject
	Context context;

	@Inject
	NotificationService notificationService;
	
	@Inject
	private LocationService locationService;
	

	private Set<IncreaseListener> listener = new HashSet<IncreaseListener>();

	private List<NearbyPlayer> lastPlayers = new ArrayList<NearbyPlayer>();
	private Set<NearbyPlayer> blockedPlayers = new HashSet<NearbyPlayer>();

	public void init() {
		locationService.init();
		locationService.registerListener(this);
	}
	
	public void registerListener(IncreaseListener increaseListener) {
		listener.add(increaseListener);
		locationService.registerListener(increaseListener);
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

		final Location userLocation = locationService.getUserLocation();
		
		if (userLocation == null) {
			Toast.makeText(context, "no location", Toast.LENGTH_SHORT).show();
			return;
		}

		for (IncreaseListener increaseListener : listener) {
			increaseListener.onFirstLocation();
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

	public void onLocationChanged(Location location) {
	}

	public void onFirstLocation() {
		updatePlayers();
	}

	public int getAccuracy() {
		return locationService.getAccuracy();
	}


}
