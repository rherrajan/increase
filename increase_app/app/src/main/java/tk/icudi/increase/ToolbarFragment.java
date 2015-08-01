package tk.icudi.increase;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import tk.icudi.increase.R;


public class ToolbarFragment extends Fragment {

    private View view;
    private AppCompatActivity activity;
    private Menu menu;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        System.out.println(" --- onAttach --- ");

        this.activity = (AppCompatActivity)activity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        this.view = inflater.inflate(R.layout.fragment_toolbar, container, false);
        System.out.println(" --- onCreateView --- ");

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.nearby_toolbar);
        activity.setSupportActionBar(toolbar);



        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        System.out.println(" --- onCreateOptionsMenu --- ");
        Log.i("Hi", "hi2");

        this.menu = menu;
        inflater.inflate(R.menu.main_activity_actions, menu);

        activateRefreshAnimation(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        System.out.println(" --- id: " + id);

        Toast.makeText(activity, "You pressed the Delete!", Toast.LENGTH_LONG).show();

        return super.onOptionsItemSelected(item);
    }



    private void activateRefreshAnimation(boolean activate) {

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


}
