package tk.icudi.view;

import java.util.List;

import roboguice.activity.RoboFragmentActivity;
import tk.icudi.NearbyPlayer;
import tk.icudi.R;
import tk.icudi.business.IncreaseListener;
import tk.icudi.business.NotificationService;
import tk.icudi.business.UpdateService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;

import com.google.inject.Inject;

public class IncreaseActivity extends RoboFragmentActivity implements IncreaseListener {

	@Inject
	private NotificationService notificationService;

	@Inject
	private UpdateService updateService;

	private ListAgentsFragment listAgentsFragment = new ListAgentsFragment();
//	private LogfilesFragment logfilesFragment = new LogfilesFragment();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_layout);

		notificationService.setActionToNotificate(IncreaseActivity.class);
		updateService.init();
		updateService.registerListener(this);
		updateService.registerListener(listAgentsFragment);

		showRefreshAnimation(false);

		if (isReadyForFragment(savedInstanceState)) {
			activateSupportFragment(listAgentsFragment, savedInstanceState);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		onPlayerRefreshSuccesfull(updateService.getLastPlayers());
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

	private Menu menu;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main_activity_actions, menu);

		this.menu = menu;
		updateAccuracy(updateService.getAccuracy());

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_refresh:
			showRefreshAnimation(true);
			updateService.updatePlayers();
			return true;

		case R.id.action_burger:

			PopupMenu popup = new PopupMenu(getActivity(), findViewById(item.getItemId()));
			popup.getMenuInflater().inflate(R.menu.burger_menu, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

				public boolean onMenuItemClick(MenuItem item) {

					switch (item.getItemId()) {
					case R.id.action_settings:
						Intent i = new Intent(getActivity(), ConfigurationActivity.class);
						startActivity(i);
						break;
					}

					return true;
				}

			});

			popup.show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private Context getActivity() {
		return this;
	}

	private void showRefreshAnimation(boolean activate) {

		if (menu == null) {
			return;
		}

		MenuItem refreshItem = menu.findItem(R.id.action_refresh);

		View actionView = refreshItem.getActionView();

		if (activate) {

			if (actionView == null) {
				refreshItem.setActionView(R.layout.iv_refresh);

				Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_refresh);
				rotation.setRepeatCount(Animation.INFINITE);
				refreshItem.getActionView().startAnimation(rotation);
			}

		} else {

			if (actionView != null) {
				actionView.clearAnimation();
				refreshItem.setActionView(null);
			}
		}
	}

	public void onPlayerRefreshStart() {
		showRefreshAnimation(true);
	}

	public void onPlayerRefreshSuccesfull(List<NearbyPlayer> players) {
		showRefreshAnimation(false);
	}

	public void onPlayerRefreshFailure(Exception exception) {
		showRefreshAnimation(false);
	}

	public void onLocationChanged(Location location) {
		final int acc;
		if (location == null) {
			acc = -1;
		} else {
			acc = (int) location.getAccuracy();
		}

		updateAccuracy(acc);
	}

	private void updateAccuracy(int acc) {

		if (menu == null) {
			return;
		}

		final String title;
		if (acc == -1) {
			title = getResources().getString(R.string.action_acc_default);
		} else {
			title = acc + "m";
		}

		menu.findItem(R.id.action_acc).setTitle(title);
	}

	public void onFirstLocation() {

	}

}
