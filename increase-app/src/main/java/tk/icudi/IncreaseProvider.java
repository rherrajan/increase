package tk.icudi;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

    	System.out.println(" --- onAppWidgetOptionsChanged --- ");
    }
}