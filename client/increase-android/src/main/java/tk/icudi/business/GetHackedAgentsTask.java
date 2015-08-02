package tk.icudi.business;

import java.util.List;

import tk.icudi.increase.CaughtPlayer;
import android.os.AsyncTask;
import android.util.Log;

abstract class GetHackedAgentsTask extends AsyncTask<String, Integer, List<CaughtPlayer>> {

	private IncreaseServer server = new IncreaseServer();
	private Exception exception;

	@Override
	protected List<CaughtPlayer> doInBackground(String... params) {
		
		try {
			List<CaughtPlayer> hackedAgents = server.getHackedAgents(params[0]);
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
