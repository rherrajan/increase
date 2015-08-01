package tk.icudi.increase.view.fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tk.icudi.CaughtPlayer;
import tk.icudi.NearbyPlayer;
import tk.icudi.increase.R;
import tk.icudi.increase.logic.IncreaseAdapter;
import tk.icudi.increase.logic.IncreaseListener;
import tk.icudi.increase.logic.UpdateService;


public class NearbyFragment extends ListFragment {

    private AgentlistNearby list;
    private UpdateService updateService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerForContextMenu(this.getListView());

        list = new AgentlistNearby(getActivity(), new ArrayList<NearbyPlayer>());
        setListAdapter(list);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterViewCompat.AdapterContextMenuInfo info = (AdapterViewCompat.AdapterContextMenuInfo) item.getMenuInfo();
        Object player_raw = getListView().getItemAtPosition(info.position);
        NearbyPlayer player = (NearbyPlayer) player_raw;

        switch (item.getItemId()) {
            case R.id.agent_ignore:
                this.updateService.blockPlayer(player);
                refreshList(updateService.getLastPlayers());
                updateService.updateNotification();
                return true;

            case R.id.agent_add:
                this.updateService.addPlayer(player);
                Toast.makeText(getActivity(), "added player " + player.getName(), Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void refreshList(List<NearbyPlayer> players) {
        if(list != null){
            list.clear();
            list.addAll(players);
        }
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        NearbyPlayer selectedValue = list.getItem(position);
        String text = "on '" + selectedValue.getLocation() + "' " + selectedValue.getHumanReadableTime() + " ago ";
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public IncreaseListener createIncreaseListener() {
        return new IncreaseAdapter() {

            @Override
            public void onNearbyAgentsRefreshSuccesfull(List<NearbyPlayer> players) {
                refreshList(players);
            }

            @Override
            public void onNearbyAgentsRefreshFailure(Exception exception) {
                Toast.makeText(getActivity(), "failed to get player information" + exception, Toast.LENGTH_SHORT).show();
                Log.e(NearbyFragment.class.getName(), "failed to get player information", exception);
            }

        };
    }
}
