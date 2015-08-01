package tk.icudi.increase.logic;

import tk.icudi.CaughtPlayer;
import android.os.AsyncTask;

abstract class AddNearbyPlayersTask extends AsyncTask<CaughtPlayer, Integer, Boolean> {

	private IncreaseServer server = new IncreaseServer();
	protected Exception exception;

	@Override
	protected Boolean doInBackground(CaughtPlayer... params) {
		try {
			return server.addNearbyPlayer(params[0]);
		} catch (Exception e) {
			this.exception = e;
			return null;
		}
	}

}
