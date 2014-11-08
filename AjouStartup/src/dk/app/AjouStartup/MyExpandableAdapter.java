package dk.app.AjouStartup;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;



class MyExpandableAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<String> mainElements;
	private HashMap<String, List<String>> childElements;
	private LayoutInflater inflater;

	public MyExpandableAdapter(Context context, List<String> mainElements,
			HashMap<String, List<String>> childElements) {
		this.context = context;
		this.mainElements = mainElements;
		this.childElements = childElements;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getGroupCount() {
		return this.mainElements.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (this.childElements.get(groupPosition) == null)
			return 0;
		return this.childElements.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.mainElements.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.childElements.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		
		 ViewHolder holder;
	        try {
	            if (convertView == null) {
	                convertView = inflater.inflate(R.layout.custom_list_view, null);
	                holder = new ViewHolder();

//	                holder.parentTitle = (TextView) convertView.findViewById(R.id.parentTitle);
	                convertView.setTag(holder);
	            } else {
	                holder = (ViewHolder) convertView.getTag();
	            }

	            holder.parentTitle.setText(mainElements.get(groupPosition));

	        } catch (Exception e) {
	        }
	        return convertView;
		/*
		if(groupPosition != 1){
			v = vi.inflate(android.R.layout.simple_list_item_1, null);
			
			v.setOnClickListener(null);
			v.setOnLongClickListener(null);
			v.setLongClickable(false);
			final TextView sectionView = (TextView) v
					.findViewById(android.R.id.text1);
			sectionView.setTextColor(Color.parseColor("#FFC800"));
			sectionView.setText( mainElements.get(groupPosition));
		}
		
		else{
			v = vi.inflate(android.R.layout.simple_list_item_2, null);
			final TextView title = (TextView) v
					.findViewById(android.R.id.text1);
			final TextView subtitle = (TextView) v
					.findViewById(android.R.id.text2);
			
			title.setText(mainElements.get(groupPosition));
			
		}
		*/
		
	}
	 public static class ViewHolder {

	        private TextView childTitle;
	        // Content
	        private TextView parentTitle;
	        private ImageView parentIcon;

	    }
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition);

        String childConfig = (String) getChild(groupPosition, childPosition);

        ViewHolder holder;
        try {
            if (convertView == null) {
                holder = new ViewHolder();

                convertView = inflater.inflate(R.layout.custom_list_view_child, null);
                holder.childTitle = (TextView) convertView.findViewById(R.id.childTitle);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.childTitle.setText(childConfig);

        } catch (Exception e) {
        }
        return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return true;
	}
}

