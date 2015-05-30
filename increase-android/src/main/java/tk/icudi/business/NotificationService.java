package tk.icudi.business;

import tk.icudi.NearbyPlayer;
import tk.icudi.R;
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

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NotificationService implements OnSharedPreferenceChangeListener {

	@Inject
	private NotificationManager notificationManager;

	@Inject
	private Vibrator vibrator;

	@Inject
	private Context context;

	private NearbyPlayer lastNotification;

	private Class<?> actionToNotificate;

	private int max_ranking_for_vibration = -1;
	private int max_ranking_for_notification = -1;
	
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

		System.out.println(" --- " + nearbyPlayer.getName() + " " + nearbyPlayer.getRank());
		System.out.println(" --- max_ranking_for_notification: " + max_ranking_for_notification);
		if (nearbyPlayer.getRank() > max_ranking_for_notification) {
			
			System.out.println(" --- cancelAll --- ");
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
		
		System.out.println(" --- lastNotification: " + lastNotification);
		
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
		
		System.out.println(" --- sendNotification --- ");
		
		Intent intent = new Intent(context, actionToNotificate);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pIntent = PendingIntent.getActivity(context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		Builder builder = new Notification.Builder(context);

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

	public void setActionToNotificate(Class<?> actionToNotificate) {
		this.actionToNotificate = actionToNotificate;
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		refreshConfiguration(sharedPreferences);
	}

}
