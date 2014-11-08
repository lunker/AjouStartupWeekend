package dk.app.AjouStartup;

import java.util.ArrayList;
import java.util.List;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class MainActivity extends ActionBarActivity {

	/*
	 * drawer
	 */
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private MyDrawerAdapter drawerAdapter = null;
	
	private final String TAG = "ajou";
	
	
//	private String[] drawerNameList = {"profile", "rental service", "communication"};
	private ListView mDrawerList;
	private List<DrawerItem> drawerNameList = null;
	
	private FragmentManager manager ; 
	private Fragment mainFragment = null;
	private Fragment rentalFragment = null;
	
	
	private int beforeSelected = -1;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	

//		drawerNameList = getResources().getStringArray(R.array.drawer_test);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		drawerNameList = new ArrayList<DrawerItem>();
		drawerNameList.add(new DrawerItem("profile",true));
		drawerNameList.add(new DrawerItem("Rental Service"));
		drawerNameList.add(new DrawerItem("Communication"));
		
		
		drawerAdapter = new MyDrawerAdapter(this, drawerNameList);
		
		// Set the adapter for the list view
		mDrawerList.setAdapter( drawerAdapter);
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mTitle = mDrawerTitle = getTitle();
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_launcher,R.string.greeting) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				
//				getActionBar().setTitle(mDrawerTitle);
				
				setTitle(R.string.title);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setDisplayHomeAsUpEnabled(tru”e);
//	    getActionBar().setHomeButtonEnabled(true);
		
		mainFragment = new MainFragment();
		rentalFragment = new RentalFragment();

		manager = getFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.add(R.id.drawer_layout, mainFragment).add(R.id.drawer_layout, rentalFragment).detach(rentalFragment);
		ft.commit();
		

	}
	
	
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        
        
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
		
		  MenuInflater inflater = getMenuInflater();
		  inflater.inflate(R.menu.main, menu);
		  return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// nagivation drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Log.i("dk", "onitemclick");
			selectItem(position);
			
		}

	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		// Create a new fragment and specify the planet to show based on
		// position
		FragmentManager fragmentManager = getFragmentManager();
		
		
		switch(beforeSelected){
		
		case -1 : fragmentManager.beginTransaction().detach(mainFragment).commit();break;
		case 0 : fragmentManager.beginTransaction().detach(mainFragment).commit();break;
		case 1:fragmentManager.beginTransaction().detach(rentalFragment).commit(); break;
		case 2:break;
		}
		beforeSelected = position;
		
		
		
		switch(position){
		case 0 : Log.i("ajou", position+"");
			fragmentManager.beginTransaction().attach( mainFragment).commit();
			break;
		case 1 : Log.i(TAG, position+"");
			
			fragmentManager.beginTransaction().attach(rentalFragment).commit();
			break; 
		case 2 : Log.i(TAG, position+"");break;
		}
		

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(drawerNameList.get(position).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);
	}
	/*
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
	*/
	

}
