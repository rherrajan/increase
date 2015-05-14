package tk.icudi.business;

import roboguice.receiver.RoboBroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;

public class AlarmReceiver extends RoboBroadcastReceiver {

	@Inject
	UpdateService updateService;

	@Override
	protected void handleReceive(Context context, Intent intent) {
		updateService.updatePlayers();
	}

}
