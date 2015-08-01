package tk.icudi.increase.view.fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.List;

import tk.icudi.CaughtPlayer;
import tk.icudi.NearbyPlayer;
import tk.icudi.increase.R;
import tk.icudi.increase.logic.IncreaseAdapter;
import tk.icudi.increase.logic.IncreaseListener;
import tk.icudi.increase.logic.UpdateService;


public class ToolbarFragment extends Fragment {

    private View toolbar;
    private AppCompatActivity activity;
    private Menu menu;

    private UpdateService updateService;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (AppCompatActivity)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (updateService != null) {
            updateService.updateNearbyAgents();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.toolbar = inflater.inflate(R.layout.fragment_toolbar, container, false);
        Toolbar toolbar = (Toolbar) this.toolbar.findViewById(R.id.nearby_toolbar);
        activity.setSupportActionBar(toolbar);

        return this.toolbar;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        this.menu = menu;
        inflater.inflate(R.menu.main_activity_actions, menu);

        showRefreshAnimation(false);
    }

    @NonNull
    public IncreaseListener createIncreaseListener() {
        return new IncreaseAdapter() {
            @Override
            public void onNearbyAgentsRefreshSuccesfull(List<NearbyPlayer> players) {
                showRefreshAnimation(false);
            }

            @Override
            public void onNearbyAgentsRefreshFailure(Exception exception) {
                showRefreshAnimation(false);
            }

            @Override
            public void onNearbyAgentsRefreshStart() {
                showRefreshAnimation(true);
            }
            
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(activity, "onLocationChanged", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFirstLocation() {
                updateService.updateNearbyAgents();
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh:
                updateService.updateNearbyAgents();
                return true;

            case R.id.action_burger:

                PopupMenu popup = new PopupMenu(getActivity(), toolbar.findViewById(item.getItemId()));
                popup.getMenuInflater().inflate(R.menu.burger_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            /*
                            case R.id.action_hacked_agents:
                                activateFragment(hackedAgentsFragment);
                                break;

                            case R.id.action_logfile:
                                activateFragment(logfilesFragment);
                                break;

                            case R.id.action_settings:
                                Intent i = new Intent(getActivity(), ConfigurationActivity.class);
                                startActivity(i);
                                break;
                            */
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

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }
}
