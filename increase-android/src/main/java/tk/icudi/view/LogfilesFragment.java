package tk.icudi.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import tk.icudi.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

public class LogfilesFragment extends RoboFragment {

	@Inject
	private Context context;

	@InjectView(R.id.logfile)
	TextView myView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.logfile, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		System.out.println(" --- onViewCreated --- ");
		
		String logs = readLogs();

		System.out.println(" --- logs: " + logs);

		myView.setText(logs);
	}

	private String readLogs() {
		
		String logs = null;
		try {
//			Runtime.getRuntime().exec("logcat -c");
			Process process = Runtime.getRuntime().exec("logcat -d *:I ");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			StringBuilder log = new StringBuilder();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				log.append(line).append("\n");
			}
			logs = log.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logs;
	}

}