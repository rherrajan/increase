package tk.icudi.business;

import tk.icudi.NearbyPlayer;
import tk.icudi.business.AddNearbyPlayersTask.AddPlayerInput;
import android.os.AsyncTask;

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
			return server.addNearbyPlayer(params[0]);
		} catch (Exception e) {
			this.exception = e;
			return null;
		}
	}

}
