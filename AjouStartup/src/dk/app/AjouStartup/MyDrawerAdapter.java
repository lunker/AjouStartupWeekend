package dk.app.AjouStartup;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDrawerAdapter extends BaseAdapter {

	private Context context = null;
	private String[] drawerItemList = null;
    
    
	public MyDrawerAdapter(Context context, String[] listItems){
	
        this.context = context;
        this.drawerItemList = listItems;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return drawerItemList.length;
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
              drawerHolder.ItemName = (TextView) view.findViewById(R.id.drawer_list_item_name);
//             drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

              view.setTag(drawerHolder);

        } else {
              drawerHolder = (DrawerItemHolder) view.getTag();

        }

//        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

//        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(dItem.getImgResID()));
        drawerHolder.ItemName.setText( drawerItemList[position]);

        return view;
  }

	  private static class DrawerItemHolder {
	        TextView ItemName;
	        ImageView icon;
	  }
		
		
}


