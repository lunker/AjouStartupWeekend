package dk.app.AjouStartup;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyDrawerAdapter extends BaseAdapter {

	private Context context = null;
	private List<DrawerItem> drawerItemList = null;
	
	
	
	public MyDrawerAdapter(Context context, List<DrawerItem> listItems){
	
        this.context = context;
        drawerItemList = listItems;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return drawerItemList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
              LayoutInflater inflater = ((Activity) context).getLayoutInflater();
              drawerHolder = new DrawerItemHolder();

              view = inflater.inflate(R.layout.drawer_list_item, parent, false);
              drawerHolder.userName = (TextView) view.findViewById(R.id.user_profile_name);
              drawerHolder.itemName = (TextView) view.findViewById(R.id.drawer_list_item_name);
              drawerHolder.headerLayout = (LinearLayout) view.findViewById(R.id.headerLayout);
              drawerHolder.itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
              drawerHolder.spinnerLayout = (LinearLayout) view.findViewById(R.id.spinnerLayout);
              drawerHolder.icon = (ImageView) view.findViewById(R.id.user_profile_image);
              
              view.setTag(drawerHolder);

        } else {
              drawerHolder = (DrawerItemHolder) view.getTag();
        }

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
        
        
        // in profile
        if (dItem.getIsUserPart()) {
        	drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.spinnerLayout.setVisibility(LinearLayout.VISIBLE);
            
            
      	  
            drawerHolder.userName.setText(drawerItemList.get(position).getItemName());	
            drawerHolder.icon.setImageResource(R.drawable.ic_launcher);
        }  
        // in item 
        else {

              drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
              drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
              drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);

              
              drawerHolder.itemName.setText( drawerItemList.get(position).getItemName());
              
              

        }
        
        return view;
  }

	  private static class DrawerItemHolder {
	        TextView itemName;
	        TextView userName;
	        ImageView icon;
	        
	        
	        LinearLayout headerLayout, itemLayout, spinnerLayout;
	        
	        
	  }
		
		
}


