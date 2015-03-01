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
		super(context, R.layout.list_mobile, values);
		this.context = context;
		this.values = values;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.list_mobile, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position].toString());

		// Change icon based on name
		String s = values[position].toString();

		System.out.println(s);

		if (s.equals("WindowsMobile")) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else if (s.equals("iOS")) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else if (s.equals("Blackberry")) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else {
			imageView.setImageResource(R.drawable.ic_launcher);
		}

		return rowView;
	}
}