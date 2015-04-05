package tk.icudi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MobileArrayAdapter extends ArrayAdapter<NearbyPlayer> {
	
	private final Context context;
	private final NearbyPlayer[] values;

	public MobileArrayAdapter(Context context, NearbyPlayer[] values) {
		super(context, R.layout.list_item, values);
		this.context = context;
		this.values = values;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.list_item, parent, false);
		TextView player_name = (TextView) rowView.findViewById(R.id.player_name);
		TextView player_time = (TextView) rowView.findViewById(R.id.player_time);
		ImageView factionPic = (ImageView) rowView.findViewById(R.id.logo);
		
		NearbyPlayer player = values[position];
		player_name.setText(player.getName());
		player_time.setText(player.getHumanReadableTime());
		
		if(player.getRank() < 1000){
			player_name.setTypeface(null, Typeface.BOLD);
		}
		
		if(player.getFaction() == Faction.blue){
			factionPic.setImageResource(R.drawable.blue);
		} else if(player.getFaction() == Faction.green){
			factionPic.setImageResource(R.drawable.green);
		} else {
			factionPic.setImageResource(R.drawable.increase);
		}
		
		return rowView;
	}

}