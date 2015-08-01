package tk.icudi.increase;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.icudi.increase.R;


public class ToolbarFragment extends Fragment {

    private View view;
    private AppCompatActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        System.out.println(" --- onAttach --- ");

        this.activity = (AppCompatActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_toolbar, container, false);
        System.out.println(" --- onCreateView --- ");

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.agent_toolbar);
        activity.setSupportActionBar(toolbar);

        return view;
    }


}
