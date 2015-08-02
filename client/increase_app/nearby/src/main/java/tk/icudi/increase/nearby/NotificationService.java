package tk.icudi.increase.nearby;

import tk.icudi.increase.Faction;
import tk.icudi.increase.NearbyPlayer;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Vibrator;
import android.preference.PreferenceManager;

public class NotificationService implements OnSharedPreferenceChangeListener {

	private NotificationManager notificationManager;
	private Vibrator vibrator;
	private Context context;

	private NearbyPlayer lastNotification;
	private Class<?> actionToNotificate;

	private int max_ranking_for_vibration;
	private int max_ranking_for_notification;

	public NotificationService(Context context){
		this.context = context;
		this.notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		this.vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public void init(){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
		refreshConfiguration(sharedPreferences);
	}
	
	private void refreshConfiguration(SharedPreferences sharedPreferences) {    
		max_ranking_for_vibration = Integer.valueOf(sharedPreferences.getString("preference_threshold_vibration", "-1"));
		max_ranking_for_notification = Integer.valueOf(sharedPreferences.getString("preference_threshold_notification", "-1"));
	}
	
	public void nearestPlayer(NearbyPlayer nearbyPlayer) {

		if (nearbyPlayer.getRank() > max_ranking_for_notification) {
			
			notificationManager.cancelAll();
			if(nearbyPlayer.getRank() < max_ranking_for_vibration){
				return;
			}
		}

		if (somethingNew(nearbyPlayer)) {
			sendNotification(nearbyPlayer);

			if (nearbyPlayer.getRank() < max_ranking_for_vibration) {
				long[] pattern = { 0, 300, 0, 300 };
				vibrator.vibrate(pattern, -1);
			}
		}
	}

	private boolean somethingNew(NearbyPlayer nearbyPlayer) {
		
		if (lastNotification != null) {
			if (lastNotification.equals(nearbyPlayer)) {
				if (lastNotification.getLocation().equals(nearbyPlayer.getLocation())) {
					return false;
				}
			}
		}

		return true;
	}

	private void sendNotification(NearbyPlayer nearbyPlayer) {
		
		Intent intent = new Intent(context, actionToNotificate);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pIntent = PendingIntent.getActivity(context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		Builder builder = new Builder(context);



		switch (nearbyPlayer.getFaction()) {
		case blue:
			builder.setSmallIcon(R.drawable.blue_small);
			break;
		case green:
			builder.setSmallIcon(R.drawable.green_small);
			break;
		}


		builder.setContentIntent(pIntent);
		builder.setContentTitle(nearbyPlayer.getName());
		builder.setContentInfo(nearbyPlayer.getHumanReadableDistance() + " " + nearbyPlayer.getDirection() + " " + nearbyPlayer.getHumanReadableTime() + " ago on " + nearbyPlayer.getLocation());

		Notification noti = builder.build();
		noti.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, noti);

		this.lastNotification = nearbyPlayer;
	}

	public void setActivityToNotificate(Class<?> actionToNotificate) {
		this.actionToNotificate = actionToNotificate;
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		refreshConfiguration(sharedPreferences);
	}

}
