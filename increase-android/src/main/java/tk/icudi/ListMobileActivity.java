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
import android.widget.ListView;
import android.widget.Toast;

public class ListMobileActivity extends ListActivity {

	private IncreaseServer server = new IncreaseServer();
	private Location userLocation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
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

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}
	
	protected void makeUseOfNewLocation(Location location) {
		this.userLocation = location;
		System.out.println("userLocation: " + userLocation);
	}

	public void onClickRefresh(View view) {
		
		// # Just for testing, allow network access in the main thread
		// # NEVER use this is productive code
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

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
		String text = "on '" + selectedValue.getLocation() + "' " + getHumanReadableTime(selectedValue) + " ago " + selectedValue.getDistance() + "m " + selectedValue.getDirection();
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	private String getHumanReadableTime(NearbyPlayer selectedValue) {
		
		int timetmp = selectedValue.getPassedSeconds();
		
		if(timetmp < 60*2){
			return timetmp + " seconds";
		}
		
		timetmp = timetmp/60;
		if(timetmp < 60*2){
			return timetmp + " minutes";
		}
		
		timetmp = timetmp/60;
		if(timetmp < 24*2){
			return timetmp + " hours";
		}
		
		timetmp = timetmp/24;
		return timetmp + " days";
	}

}