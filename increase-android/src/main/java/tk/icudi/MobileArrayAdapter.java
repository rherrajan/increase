package tk.icudi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MobileArrayAdapter extends ArrayAdapter<Unit> {
	
	private final Context context;
	private final Unit[] values;

	public MobileArrayAdapter(Context context, Unit[] values) {
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
		Unit unit = values[position];
		textView.setText(unit.toString());

		int alphaReduce = unit.getPassedSeconds() / 100;
		if(alphaReduce > 100){
			alphaReduce = 100;
		}
		
		imageView.setImageAlpha(255 - alphaReduce);
		imageView.setImageResource(R.drawable.increase);

		return rowView;
	}
}