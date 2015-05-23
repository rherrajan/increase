package tk.icudi.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import tk.icudi.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LogfilesFragment extends RoboFragment {

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

		myView.setText(logs);
	}

	private String readLogs() {
		
		List<String> logEntries = new ArrayList<String>();
					
		
		try {
			Process process = Runtime.getRuntime().exec("logcat -d *:I ");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				logEntries.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.reverse(logEntries);
		
		StringBuilder builder = new StringBuilder();
		for (String logEntry : logEntries) {
			builder.append(logEntry).append("\n");
		}
		return builder.toString();
	}

}