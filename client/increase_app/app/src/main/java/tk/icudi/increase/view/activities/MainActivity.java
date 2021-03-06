package tk.icudi.increase.view.activities;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tk.icudi.increase.R;
import tk.icudi.increase.nearby.UpdateService;
import tk.icudi.increase.view.fragments.NearbyFragment;
import tk.icudi.increase.view.fragments.ToolbarFragment;

public class MainActivity extends AppCompatActivity {

    public static UpdateService updateService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        super.onCreate(savedInstanceState);

        setContentView(tk.icudi.increase.R.layout.activity_main);

        init();
    }

    private void init() {
        updateService = new UpdateService(this, AlarmReceiver.class);

        ToolbarFragment toolbar = (ToolbarFragment)getFragmentManager().findFragmentById(R.id.fragment_toolbar);
        toolbar.setUpdateService(updateService);
        updateService.registerListener(toolbar.createIncreaseListener());

        NearbyFragment nearbyList = (NearbyFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_nearby);
        nearbyList.setUpdateService(updateService);
        updateService.registerListener(nearbyList.createIncreaseListener());

        updateService.init();
    }
}
