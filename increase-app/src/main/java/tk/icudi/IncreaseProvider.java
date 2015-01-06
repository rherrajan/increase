package tk.icudi;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class IncreaseProvider extends AppWidgetProvider {

    public IncreaseProvider() {

    }

    @Override
    public void onEnabled(Context context) {
    	
    	System.out.println(" --- onEnabled --- ");
    	
    }

    @Override
    public void onReceive(Context ctx, Intent intent) {
        final String action = intent.getAction();
        
        System.out.println(" --- onReceive --- ");

        super.onReceive(ctx, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    	System.out.println(" --- onUpdate --- ");

        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, HelloAndroidActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.button_refresh, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

    	System.out.println(" --- onAppWidgetOptionsChanged --- ");
    }
}