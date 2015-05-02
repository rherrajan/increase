package tk.icudi;

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
		
		if(nearbyPlayer.getRank() < UpdateService.max_ranking_for_notification){
			if(somethingNew(nearbyPlayer)){
				sendNotification(nearbyPlayer);
			}
			
			if(nearbyPlayer.getRank() < UpdateService.max_ranking_for_vibration){
				System.out.println(" --- --- --- ");
				vibrator.vibrate(500);
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
		builder.setSmallIcon(R.drawable.increase_small);
		builder.setContentIntent(pIntent);
		builder.setContentTitle(nearbyPlayer.getName());
		builder.setContentInfo("on " + nearbyPlayer.getLocation() + ". " + nearbyPlayer.getHumanReadableTime() + " ago. " + nearbyPlayer.getHumanReadableDistance() + " " + nearbyPlayer.getDirection());
		
		Notification noti =	builder.build();
		noti.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, noti);
		
		this.lastNotification = nearbyPlayer;
	}

}
