package tk.icudi.view;

import roboguice.activity.RoboFragmentActivity;
import tk.icudi.R;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class ListMobileActivity extends RoboFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		if(isReadyForFragment(savedInstanceState)){
			activateSupportFragment(new ListAgentsFragment(), savedInstanceState);
		}
		
	}

	private boolean isReadyForFragment(Bundle savedInstanceState) {
		if (findViewById(R.id.fragment_container) == null) {
			return false;
		}

		if (savedInstanceState != null) {
			return false;
		}
		
		return true;
	}

	private void activateSupportFragment(android.support.v4.app.Fragment fragment, Bundle savedInstanceState) {
		fragment.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
	}
	
}