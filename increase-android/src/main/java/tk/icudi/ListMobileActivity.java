package tk.icudi;

import roboguice.activity.RoboFragmentActivity;
import android.os.Bundle;

public class ListMobileActivity extends RoboFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	System.out.println(" --- ListMobileActivity --- ");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        
        System.out.println(" --- ListMobileActivity end--- ");
    }
    
}