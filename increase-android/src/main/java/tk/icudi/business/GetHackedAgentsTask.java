package tk.icudi.business;

import java.util.List;

import tk.icudi.CaughtPlayer;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

abstract class GetHackedAgentsTask extends AsyncTask<Void, Integer, List<CaughtPlayer>> {

	private IncreaseServer server = new IncreaseServer();
	private Exception exception;

	@Override
	protected List<CaughtPlayer> doInBackground(Void... params) {
		
		try {
			List<CaughtPlayer> hackedAgents = server.getHackedAgents(null);
			Log.i(UpdateService.class.getName(), "found " + hackedAgents.size() + " hackedAgents");
			
			return hackedAgents;

		} catch (Exception e) {
			this.exception = e;
			return null;
		}
	}

	@Override
	protected void onPostExecute(List<CaughtPlayer> players) {
		if (players != null) {
			onSuccessfullExecute(players);
		} else {
			onFailure(exception);
		}
	}

	protected abstract void onFailure(Exception exception);

	protected abstract void onSuccessfullExecute(List<CaughtPlayer> players);

}
