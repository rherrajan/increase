package tk.icudi.increase.view.fragments;

import java.util.List;

import tk.icudi.Faction;
import tk.icudi.NearbyPlayer;
import tk.icudi.increase.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AgentlistNearby extends Agentlist<NearbyPlayer> {

	private int max_ranking_for_bold_display;

	public AgentlistNearby(Context context, List<NearbyPlayer> players) {
		super(context, R.layout.agent_nearby, players);
	}

	@Override
	protected int getAgentLayout() {
		return R.layout.agent_nearby;
	}
	
	protected void refreshConfiguration(SharedPreferences sharedPreferences) {
		max_ranking_for_bold_display = Integer.valueOf(sharedPreferences.getString("preference_threshold_bold", "-1"));
	}

	protected void modifyView(View rowView, NearbyPlayer agent) {
		TextView player_name = (TextView) rowView.findViewById(R.id.player_name);
		TextView player_distance = (TextView) rowView.findViewById(R.id.player_distance);
		ImageView factionPic = (ImageView) rowView.findViewById(R.id.logo);
		ImageView directionPic = (ImageView) rowView.findViewById(R.id.direction);

		player_name.setText(agent.getName());
		player_distance.setText(agent.getHumanReadableDistance());

		if (agent.getRank() < max_ranking_for_bold_display) {
			player_name.setTypeface(null, Typeface.BOLD);
		}

		if (agent.getFaction() == Faction.blue) {
			factionPic.setImageResource(R.drawable.blue);
		} else if (agent.getFaction() == Faction.green) {
			factionPic.setImageResource(R.drawable.green);
		} else {
			factionPic.setImageResource(R.drawable.increase);
		}

		directionPic.setRotation(-90 + (int) agent.getAngle());

	}


}