package tk.icudi.view;

import roboguice.activity.RoboPreferenceActivity;
import tk.icudi.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ConfigurationActivity extends RoboPreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new ConfigurationFragment()).commit();
	}

	private class ConfigurationFragment extends PreferenceFragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);
		}
	}

}
