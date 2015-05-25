package tk.icudi.view;

import java.util.List;

import tk.icudi.CaughtPlayer;
import tk.icudi.R;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AgentlistHacked extends Agentlist<CaughtPlayer> {

	private static final int max_ranking_for_white = 500;
	private static final int min_ranking_for_grey = 1500;
	
	public AgentlistHacked(Context context, List<CaughtPlayer> players) {
		super(context, R.layout.agent_hacked, players);
	}

	protected int getAgentLayout() {
		return R.layout.agent_hacked;
	}
	
	protected void modifyView(View rowView, CaughtPlayer agent) {
		TextView player_name = (TextView) rowView.findViewById(R.id.player_name);
		ImageView white = (ImageView) rowView.findViewById(R.id.ying_yang_white);


		player_name.setText(agent.getName());
		white.setImageAlpha(calculateAlpha(agent));
	}

	private int calculateAlpha(CaughtPlayer agent) {
		
		long rank = agent.getRank();
		
		int alpha = 255-(int)(255 * ((rank-max_ranking_for_white) / (double)(min_ranking_for_grey - max_ranking_for_white)));
		
		if(alpha > 255){
			alpha = 255;
		} else if(alpha < 0){
			alpha = 0;
		}
		
//		Log.d(this.getClass().getSimpleName(), agent.getName() + ": rank=" + agent + " => aplha=" + alpha);
		
		return alpha;
	}


}