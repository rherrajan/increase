package tk.icudi;

import roboguice.activity.RoboFragmentActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ListMobileActivity extends RoboFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		activateFragment(new ListAgentsFragment(), savedInstanceState);
	}

	private void activateFragment(Fragment fragment, Bundle savedInstanceState) {

		if (findViewById(R.id.fragment_container) == null) {
			return;
		}

		if (savedInstanceState != null) {
			return;
		}

		fragment.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
	}

}