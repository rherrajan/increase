package tk.icudi.business;

import java.util.List;

import tk.icudi.NearbyPlayer;
import tk.icudi.business.AddNearbyPlayersTask.AddPlayerInput;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

abstract class AddNearbyPlayersTask extends AsyncTask<AddPlayerInput, Integer, Boolean> {

	private IncreaseServer server = new IncreaseServer();
	protected Exception exception;

	static class AddPlayerInput {
		public NearbyPlayer player;
		public int accuracy;
	}


	@Override
	protected Boolean doInBackground(AddPlayerInput... params) {
		try {
			return server.addNearbyPlayers(params[0]);
		} catch (Exception e) {
			this.exception = e;
			return null;
		}
	}

}
