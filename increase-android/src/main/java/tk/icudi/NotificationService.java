package tk.icudi;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NotificationService {

	@Inject
	NotificationManager notificationManager;

	@Inject
	Context context;

	public void sendNotification(NearbyPlayer nearbyPlayer) {
		
		Intent intent = new Intent(context, ListMobileActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

		Builder builder = new Notification.Builder(context);
		builder.setSmallIcon(R.drawable.increase_small);
		builder.setContentIntent(pIntent);
		builder.setContentTitle(nearbyPlayer.getName());
		builder.setContentInfo("on " + nearbyPlayer.getLocation() + ". " + nearbyPlayer.getHumanReadableTime() + " ago. " + nearbyPlayer.getHumanReadableDistance() + " " + nearbyPlayer.getDirection());
		
		Notification noti =	builder.build();
		noti.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, noti);
	}

}
