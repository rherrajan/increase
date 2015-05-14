package tk.icudi;

import roboguice.activity.RoboFragmentActivity;
import android.os.Bundle;

public class ListMobileActivity extends RoboFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
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