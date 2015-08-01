package tk.icudi.increase.view.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import tk.icudi.increase.R;
import tk.icudi.increase.logic.UpdateService;
import tk.icudi.increase.view.fragments.NearbyFragment;
import tk.icudi.increase.view.fragments.ToolbarFragment;

public class MainActivity extends AppCompatActivity {

    private UpdateService updateService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(tk.icudi.increase.R.layout.activity_main);

        init();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(parent, name, context, attrs);

        return view;
    }

    private void init() {
        this.updateService = new UpdateService(this);

        ToolbarFragment toolbar = (ToolbarFragment)getFragmentManager().findFragmentById(R.id.fragment_toolbar);
        toolbar.setUpdateService(updateService);
        updateService.registerListener(toolbar.createIncreaseListener());

        NearbyFragment nearbyList = (NearbyFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_nearby);
        nearbyList.setUpdateService(updateService);
        updateService.registerListener(nearbyList.createIncreaseListener());

        updateService.init();
    }
}
