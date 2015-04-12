package tk.icudi;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ListMobileActivity extends ListActivity {

	private IncreaseServer server = new IncreaseServer();
	private Location userLocation;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		Button button = (Button) findViewById(R.id.button_refresh);
		button.setEnabled(false);
		
		setUpLocationService();
	}

	private void setUpLocationService() {
		
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		
		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {


		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}

			public void onLocationChanged(Location location) {
				 makeUseOfNewLocation(location);
			}
		  };

		if((locationManager.getProvider(LocationManager.NETWORK_PROVIDER) == null) == false){
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		} else {
			// emulator
			Location dummyLoc = new Location("dummyprovider");
			dummyLoc.setLatitude(50.107356);
			dummyLoc.setLongitude(8.664123);
			dummyLoc.setAccuracy(815);
			makeUseOfNewLocation(dummyLoc);
		}
		
	}
	
	protected void makeUseOfNewLocation(Location location) {
		this.userLocation = location;
		System.out.println("userLocation: " + userLocation);
		
		if(userLocation != null){
			updateButton((int)location.getAccuracy());
		}
	}

	private void updateButton(int acc) {
		Button button = (Button) findViewById(R.id.button_refresh);
		button.setText("Refresh (" + acc +"m acc)");
		
		if(acc < 2000){
			button.setEnabled(true);
		} else {
			button.setEnabled(false);
		}
	}

	public void onClickRefresh(View view) {
		
		// # Just for testing, allow network access in the main thread
		// # NEVER use this is productive code
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
				
		if(userLocation == null){
			Toast.makeText(this, "no location", Toast.LENGTH_SHORT).show();
			return;
		}
				
		try {
			
			List<NearbyPlayer> players = server.getNearbyPlayers(userLocation);
			Log.i(ListMobileActivity.class.getName(),"found " + players.size() + " players");
			
			setListAdapter(new MobileArrayAdapter(this, players.toArray(new NearbyPlayer[0])));
			
		} catch (Exception e) {
			Toast.makeText(this, "failed to get player information" + e, Toast.LENGTH_SHORT).show();
			Log.e(ListMobileActivity.class.getName(), "failed to get player information", e);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		NearbyPlayer selectedValue = (NearbyPlayer) getListAdapter().getItem(position);
		String text = "on '" + selectedValue.getLocation() + "' " + selectedValue.getHumanReadableTime() + " ago " ;
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}




}