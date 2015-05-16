package tk.icudi.business;

import java.util.List;

import tk.icudi.NearbyPlayer;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

abstract class GetNearbyPlayersTask extends AsyncTask<Location, Integer, List<NearbyPlayer>> {

	private IncreaseServer server = new IncreaseServer();
	private Exception exception;




	@Override
	protected List<NearbyPlayer> doInBackground(Location... params) {
		try {
			List<NearbyPlayer> players = server.getNearbyPlayers(params[0]);
			Log.i(UpdateService.class.getName(), "found " + players.size() + " players");
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

	protected abstract void onSuccessfullExecute(List<NearbyPlayer> players);

}
