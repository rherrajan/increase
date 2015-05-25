package tk.icudi.view;

import java.util.List;

import tk.icudi.CaughtPlayer;
import tk.icudi.R;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AgentlistHacked extends Agentlist<CaughtPlayer> {

	public AgentlistHacked(Context context, List<CaughtPlayer> players) {
		super(context, R.layout.agent_hacked, players);
	}

	protected int getAgentLayout() {
		return R.layout.agent_hacked;
	}
	
	protected void modifyView(View rowView, CaughtPlayer agent) {
		TextView player_name = (TextView) rowView.findViewById(R.id.player_name);
		ImageView logo = (ImageView) rowView.findViewById(R.id.logo);

		player_name.setText(agent.getName());
		logo.setImageResource(R.drawable.increase);
	}


}