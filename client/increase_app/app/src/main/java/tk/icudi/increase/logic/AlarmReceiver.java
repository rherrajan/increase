package tk.icudi.increase.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	private UpdateService updateService;

	public AlarmReceiver(UpdateService updateService){
		this.updateService = updateService;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		updateService.updateNearbyAgents();
	}
}
