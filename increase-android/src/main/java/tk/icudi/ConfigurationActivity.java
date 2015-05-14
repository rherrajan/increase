package tk.icudi;

import roboguice.activity.RoboPreferenceActivity;
import tk.icudi.business.AlarmService;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.google.inject.Inject;

public class ConfigurationActivity extends RoboPreferenceActivity {

	@Inject
	AlarmService alarmService;

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
			getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(ConfigurationActivity.this.alarmService);
		}
	}

}
