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
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		
		NearbyPlayer player = values[position];
		textView.setText(player.getName());

		if(player.getRank() < 1000){
			textView.setTypeface(null, Typeface.BOLD);
		}
		
		if(player.getFaction() == Faction.blue){
			imageView.setImageResource(R.drawable.blue);
		} else if(player.getFaction() == Faction.green){
			imageView.setImageResource(R.drawable.green);
		} else {
			imageView.setImageResource(R.drawable.increase);
		}
		
		return rowView;
	}

//	private int calculateAlpha(NearbyPlayer player) {
//		int alphaReduce = player.getPassedSeconds() / 100;
//		if(alphaReduce > 50){
//			alphaReduce = 50;
//		}
//		
//		int alpha = 255 - alphaReduce;
//		return alpha;
//	}
}