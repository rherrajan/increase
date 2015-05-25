package tk.icudi.view;

import java.util.List;

import tk.icudi.Faction;
import tk.icudi.NearbyPlayer;
import tk.icudi.Player;
import tk.icudi.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class Agentlist<T extends Player> extends ArrayAdapter<T> implements OnSharedPreferenceChangeListener {

	private final Context context;
	private final List<T> players;

	public Agentlist(Context context, int layout, List<T> players) {
		super(context, layout, players);
		this.context = context;
		this.players = players;
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
		refreshConfiguration(sharedPreferences);
	}


	protected void refreshConfiguration(SharedPreferences sharedPreferences) {
		
	}


	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(getAgentLayout(), parent, false);
		
		T player = players.get(position);
		
		modifyView(rowView, player);

		return rowView;
	}


	protected abstract void modifyView(View rowView, T player);
	protected abstract int getAgentLayout();


	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		refreshConfiguration(sharedPreferences);
	}

}