package tk.icudi.business;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AlarmService implements OnSharedPreferenceChangeListener {

	@Inject
	AlarmManager alarmManager;
	
	@Inject
	Context context;
	
	private boolean isInitialised = false;
	private PendingIntent updateAlarm;
	private boolean doAutoUpdates = false;

	public synchronized void init() {
		if(isInitialised){
			return;
		}
		isInitialised = true;
		
		Intent unpendingIntent = new Intent(context, AlarmReceiver.class);
		this.updateAlarm = PendingIntent.getBroadcast(context, 0, unpendingIntent, 0);
		
		alarmManager.cancel(updateAlarm);
	}

	private void aktivateAutoUpdates(boolean doAutoUpdates) {

		this.doAutoUpdates = doAutoUpdates;
		if (doAutoUpdates) {
			alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), UpdateService.seconds_till_player_refresh * 1000, updateAlarm);
		} else {
			alarmManager.cancel(updateAlarm);
		}
	}
	
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if(key.equals("checkbox_preference")){
			boolean newValue = sharedPreferences.getBoolean(key, false);
			aktivateAutoUpdates(newValue);
		}
	}
}
