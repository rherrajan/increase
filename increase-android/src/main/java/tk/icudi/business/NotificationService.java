package tk.icudi.business;

import tk.icudi.ListMobileActivity;
import tk.icudi.NearbyPlayer;
import tk.icudi.R;
import android.app.ListFragment;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NotificationService {

	@Inject
	NotificationManager notificationManager;

	@Inject
	Vibrator vibrator;

	@Inject
	Context context;

	private NearbyPlayer lastNotification;

	public void nearestPlayer(NearbyPlayer nearbyPlayer) {
		
		if(nearbyPlayer.getRank() > UpdateService.max_ranking_for_notification){
			notificationManager.cancelAll();
			return;
		}

		if(somethingNew(nearbyPlayer)){
			sendNotification(nearbyPlayer);

			if(nearbyPlayer.getRank() < UpdateService.max_ranking_for_vibration){
				long[] pattern = {0, 300, 0, 300};
				vibrator.vibrate(pattern, -1);
			}
		}
	}

	private boolean somethingNew(NearbyPlayer nearbyPlayer) {
		if(lastNotification != null){
			if(lastNotification.equals(nearbyPlayer)){
				if(lastNotification.getLocation().equals(nearbyPlayer.getLocation())){
					return false;
				}
			}
		}
		
		return true;
	}

	private void sendNotification(NearbyPlayer nearbyPlayer) {
		Intent intent = new Intent(context, ListMobileActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pIntent = PendingIntent.getActivity(context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		    
		Builder builder = new Notification.Builder(context);
		
		switch(nearbyPlayer.getFaction()){
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
		
		Notification noti =	builder.build();
		noti.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, noti);
		
		this.lastNotification = nearbyPlayer;
	}

}
