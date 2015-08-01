package tk.icudi.increase.logic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

class AlarmService implements OnSharedPreferenceChangeListener {

	private AlarmManager alarmManager;

	private Context context;

	private boolean isInitialised = false;
	private PendingIntent updateAlarm;

	private int seconds_till_player_refresh;

	private boolean doAutoUpdates;

	public AlarmService(Context context){
		this.context = context;
		this.alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	}

	public synchronized void init() {
		if (isInitialised) {
			return;
		}
		isInitialised = true;

		Intent unpendingIntent = new Intent(context, AlarmReceiver.class);
		this.updateAlarm = PendingIntent.getBroadcast(context, 0, unpendingIntent, 0);
		alarmManager.cancel(updateAlarm);

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
		this.doAutoUpdates = sharedPreferences.getBoolean("preference_auto_updates", false);
		this.seconds_till_player_refresh = Integer.valueOf(sharedPreferences.getString("preference_update_interval", "-1"));
		refreshAutoUpdateSettings();
	}

	private void refreshAutoUpdateSettings() {

		if (doAutoUpdates && seconds_till_player_refresh > 0) {
			alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), seconds_till_player_refresh * 1000, updateAlarm);
		} else {
			alarmManager.cancel(updateAlarm);
		}

	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals("preference_auto_updates")) {
			this.doAutoUpdates = sharedPreferences.getBoolean(key, false);
		} else if (key.equals("preference_update_interval")) {
			this.seconds_till_player_refresh = Integer.valueOf(sharedPreferences.getString(key, "-1"));
		}

		refreshAutoUpdateSettings();
	}
}
