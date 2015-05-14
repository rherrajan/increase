package tk.icudi;

import roboguice.activity.RoboFragmentActivity;
import android.os.Bundle;

public class ListMobileActivity extends RoboFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }
    
}