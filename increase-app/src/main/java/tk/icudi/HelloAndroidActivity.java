package tk.icudi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class HelloAndroidActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(tk.icudi.R.menu.main, menu);
		return true;
	}

}
