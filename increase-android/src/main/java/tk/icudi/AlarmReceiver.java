package tk.icudi;

import roboguice.receiver.RoboBroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.inject.Inject;

public class AlarmReceiver extends RoboBroadcastReceiver {

	@Inject
	UpdateService updateService;
	
	private boolean isInitialised = false;
	
	@Override
	protected void handleReceive(Context context, Intent intent) {
        // For our recurring task, we'll just display a message
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        
        System.out.println(" --- I'm running --- ");
	}
	
}
