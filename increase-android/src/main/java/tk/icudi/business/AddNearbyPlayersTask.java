package tk.icudi.business;

import tk.icudi.business.AddNearbyPlayersTask.AddPlayerInput;
import android.os.AsyncTask;
import android.provider.Settings.Secure;

abstract class AddNearbyPlayersTask extends AsyncTask<AddPlayerInput, Integer, Boolean> {

	private IncreaseServer server = new IncreaseServer();
	protected Exception exception;

	static class AddPlayerInput {
		public String playername;
		public int accuracy;
		public String device_id;
		
		public String makeQueryString() {
			return "player=" + playername + "&accuracy=" + accuracy+ "&id=" + device_id;
		}
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
