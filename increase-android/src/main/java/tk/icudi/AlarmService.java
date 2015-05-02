package tk.icudi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AlarmService {

	@Inject
	AlarmManager alarmManager;
	
	@Inject
	Context context;
	
	private boolean isInitialised = false;
	private PendingIntent updateAlarm;
	private boolean doAutoUpdates = false;

	synchronized void init() {
		if(isInitialised){
			System.out.println(" --- doppelt: ");
			return;
		}
		isInitialised = true;
		
		Intent unpendingIntent = new Intent(context, AlarmReceiver.class);
		this.updateAlarm = PendingIntent.getBroadcast(context, 0, unpendingIntent, 0);
		
		System.out.println(" --- updateAlarm: " + updateAlarm);
		
		aktivateAutoUpdates(false);
	}

	public void aktivateAutoUpdates(boolean doAutoUpdates) {

		if (this.doAutoUpdates == doAutoUpdates) {
			return;
		}

		this.doAutoUpdates = doAutoUpdates;
		if (doAutoUpdates) {
			alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), UpdateService.seconds_till_player_refresh * 1000, updateAlarm);
			Toast.makeText(context, "alarm set", Toast.LENGTH_SHORT).show();
			System.out.println(" --- alarm set --- ");
		} else {
			alarmManager.cancel(updateAlarm);
			Toast.makeText(context, "alarm canceled", Toast.LENGTH_SHORT).show();
			System.out.println(" --- alarm canceled --- ");
		}
	}

	public boolean isAutoUpdates() {
		return doAutoUpdates;
	}
}
