package in.nitkkr.placements.adapters;

import in.nitkkr.placements.constants.ApplicationConstants;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.placementapplication.R;

public class NavigationDrawerAdapter extends BaseAdapter {
	String[] menuItems;
	TypedArray menuIcons;
	Context context;

	public NavigationDrawerAdapter(Context context, String[] menuItems,
			TypedArray menuIcons) {
		this.context = context;
		this.menuItems = menuItems;
		this.menuIcons = menuIcons;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position == 0) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.header_navigation_drawer,
					parent, false);
			TextView name = (TextView) convertView
					.findViewById(R.id.navigation_drawer_name);
			TextView email = (TextView) convertView
					.findViewById(R.id.navigation_drawer_email);
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					ApplicationConstants.SHARED_PREFERENCES, 0);
			name.setText(sharedPreferences.getString(ApplicationConstants.NAME,
					""));
			email.setText(sharedPreferences.getString(
					ApplicationConstants.EMAIL, ""));
			return convertView;
		} else {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_navigation_drawer,
					parent, false);

			TextView itemNameTextView = (TextView) convertView
					.findViewById(R.id.item_name);
			ImageView itemIconImageView = (ImageView) convertView
					.findViewById(R.id.item_icon);
			itemNameTextView.setText(menuItems[position - 1]);
			itemIconImageView.setImageResource(menuIcons.getResourceId(
					position - 1, -1));
			return convertView;
		}
	}

	@Override
	public int getCount() {
		return menuItems.length + 1;
	}

	@Override
	public Object getItem(int pos) {
		return menuItems[pos - 1];
	}

	@Override
	public long getItemId(int itemId) {
		return itemId;
	}
}