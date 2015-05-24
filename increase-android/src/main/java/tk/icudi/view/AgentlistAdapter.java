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

public class AgentlistAdapter<T extends Player> extends ArrayAdapter<T> implements OnSharedPreferenceChangeListener {

	private final Context context;
	private final List<T> players;

	private int max_ranking_for_bold_display = -1;

	public AgentlistAdapter(Context context, List<T> players) {
		super(context, R.layout.agent_list_item, players);
		this.context = context;
		this.players = players;
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
		refreshConfiguration(sharedPreferences);
	}

	
	private void refreshConfiguration(SharedPreferences sharedPreferences) {
		max_ranking_for_bold_display = Integer.valueOf(sharedPreferences.getString("max_ranking_for_bold_display", "-1"));
	}
	

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.agent_list_item, parent, false);
		TextView player_name = (TextView) rowView.findViewById(R.id.player_name);
		TextView player_distance = (TextView) rowView.findViewById(R.id.player_distance);
		ImageView factionPic = (ImageView) rowView.findViewById(R.id.logo);
		ImageView directionPic = (ImageView) rowView.findViewById(R.id.direction);

		T player = players.get(position);
		
		player_name.setText(player.getName());
		
		if(player.getClass().equals(NearbyPlayer.class)){
		
			NearbyPlayer nearbyPlayer = (NearbyPlayer)player;
			player_distance.setText(nearbyPlayer.getHumanReadableDistance());
			
			if (nearbyPlayer.getRank() < max_ranking_for_bold_display) {
				player_name.setTypeface(null, Typeface.BOLD);
			}
			
			if (nearbyPlayer.getFaction() == Faction.blue) {
				factionPic.setImageResource(R.drawable.blue);
			} else if (nearbyPlayer.getFaction() == Faction.green) {
				factionPic.setImageResource(R.drawable.green);
			} else {
				factionPic.setImageResource(R.drawable.increase);
			}
			
			directionPic.setRotation(-90 + (int) nearbyPlayer.getAngle());
		}

		return rowView;
	}


	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		refreshConfiguration(sharedPreferences);
	}

}