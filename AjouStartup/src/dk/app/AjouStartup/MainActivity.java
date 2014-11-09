package dk.app.AjouStartup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ActionBar;
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
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import dk.app.AjouStartup.communication.CommunicationFragment;
import dk.app.AjouStartup.profile.ProfileFragment;
import dk.app.AjouStartup.rental.RentalFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class MainActivity extends ActionBarActivity {

	/*
	 * drawer
	 */
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;


	private MyDrawerAdapter drawerAdapter = null;
	
	private final String TAG = "ajou";
	
	
//	private ExpandableListView mDrawerList;
	private ListView mDrawerList;
	private List<DrawerItem> drawerNameList = null;
	
	private FragmentManager manager ; 
	private Fragment mainFragment = null;
	private Fragment rentalFragment = null;
	private Fragment profileFragment = null;
	
	private int beforeSelected = -1;
	String[] categorys = {"profile", "rental", "communication"};
	String[] subCategorys = {"Wedding", "Interview", "Picnic"};
	
	private List<String> mainCategory = null;
	private HashMap<String, List<String>> subCategory = new HashMap<String, List<String>>();
	private List<String> rentalCategroy = new ArrayList<String>();
//	private List<String> subCategory = null;
	
	
	
	 private CharSequence mDrawerTitle;
	    private CharSequence mTitle;
	    private String[] mPlanetTitles;
	    private int selectedPosition;
    private ExpandableListView exListView=  null;
    
    private List<String> listParent;
    HashMap<String, List<String>> listDataChild;
    CustomExpandAdapter customAdapter;
    private LinearLayout navDrawerView;
    private BackPressCloseHandler backPressCloseHandler;
    
    /*
     * 
     * group-
     * -1 ; main
     * 0  ; profile
     * 1 ; rental
     * 2 ; communication
     */
    
    /*
     * child  -
     * 
     * 0 : christmas
     * 1 : wedding
     * 
     */
    
    /**
     * isshowing
     * 0 ;group
     * 1 ; child
     */
    int isShowing = -1;
    int groupClicked = -2;
    int childClicked = -2;
	public float getpixels(int dp){

        //Resources r = boardContext.getResources();
        //float px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpis, r.getDisplayMetrics());

         final float scale = getResources().getDisplayMetrics().density;
            int px = (int) (dp * scale + 0.5f);
        return px;
    }
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		Log.i("ajou",getpixels(200)+"");
		backPressCloseHandler = new BackPressCloseHandler(this);
		
		navDrawerView = (LinearLayout) findViewById(R.id.navDrawerView);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
//		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		drawerNameList = new ArrayList<DrawerItem>();
		drawerNameList.add(new DrawerItem("profile",true));
		drawerNameList.add(new DrawerItem("Rental Service"));
		drawerNameList.add(new DrawerItem("Communication"));
		
		drawerAdapter = new MyDrawerAdapter(this, drawerNameList);
		
		// Set the adapter for the list view
		// Set the list's click listener
//		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//		mDrawerList.setAdapter( drawerAdapter);
//		mDrawerList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, categorys));
//		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.test_test, categorys));
		
		mTitle = mDrawerTitle = getTitle();
		
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
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
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		if(mainFragment == null){
			
			mainFragment = new MainFragment();
			rentalFragment = new RentalFragment();
			profileFragment = new ProfileFragment();
			
			manager = getFragmentManager();
			FragmentTransaction ft = manager.beginTransaction();
			ft.add(R.id.content_frame, mainFragment)
			.add(R.id.content_frame, rentalFragment)
			.add(R.id.content_frame,profileFragment)
			.detach(rentalFragment)
			.detach(profileFragment);
			ft.commit();
		}
		
		/**
		 * 
		 * 
		 */
		
		exListView = (ExpandableListView) findViewById(R.id.nav_left_drawer);
		listParent = new ArrayList<String>();
	    listDataChild = new HashMap<String, List<String>>();
	    
	    listParent.add("profile");
        listParent.add("rental");
        listParent.add("communication");

        listDataChild.put("profile", new ArrayList<String>());
        listDataChild.put("rental", Arrays.asList(subCategorys));

        listDataChild.put("communication", new ArrayList<String>());

        customAdapter = new CustomExpandAdapter(this, listParent, listDataChild);
        // setting list adapter
        
        exListView.setAdapter(customAdapter);
        exListView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
//        mDrawerList.setAdapter(customAdapter);
//        mDrawerList.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
        
        
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        exListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            	
                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPosition));
                parent.setItemChecked(index, true);

                Log.i("ajou", "onGroupclick : " +  groupPosition);
                
                String parentTitle = ((String) customAdapter.getGroup(groupPosition));

                
                selectGroupItem(groupPosition);

                return false;
            }
        });

        
        
        
        exListView.setOnChildClickListener(new OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

            	Log.i("ajou", "onChildClick : " +  childPosition);
                Log.d("CHECK", "child click");

                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);

                selectChildItem(childPosition);

                return false;
            }
        });
    }
	
	
	@Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
	
	
	private void selectGroupItem(int position) {
	        selectedPosition = position;
	        // update the main content by replacing fragments
	       	        // update selected item and title, then close the drawer
	        // mDrawerList.setItemChecked(selectedPosition, true);
	        
	        
//	        setTitle("hi");
	        if(position == -1){
	        	mainFragment = new MainFragment();
	        	getFragmentManager().beginTransaction().replace(R.id.content_frame, mainFragment).commit();
	        	setTitle("Main");
	        	mDrawerLayout.closeDrawer(navDrawerView);
	        }
	        else if(position == 0){
	        	setTitle("Profile");
	        	profileFragment = new ProfileFragment();
	        	getFragmentManager().beginTransaction().replace(R.id.content_frame, profileFragment).commit();
	        	mDrawerLayout.closeDrawer(navDrawerView);
	        }
	        else if(position == 1){
	        	;
	        }
	        else{
	        	setTitle("Communication");
	        	Fragment cmcFragment = new CommunicationFragment();
	        	getFragmentManager().beginTransaction().replace(R.id.content_frame, cmcFragment).commit();
	        	mDrawerLayout.closeDrawer(navDrawerView);
	        }
	    }
	

	 
	 private void selectChildItem(int position) {
	        selectedPosition = position;
	        mDrawerLayout.closeDrawer(navDrawerView);

	        // update the main content by replacing fragments
	       	        // update selected item and title, then close the drawer
	        // mDrawerList.setItemChecked(selectedPosition, true);
	        
	        if(position == 0){
	        	setTitle("Rental");
	        	
	        	rentalFragment = new RentalFragment(0);
	        	getFragmentManager().beginTransaction().replace(R.id.content_frame, rentalFragment).commit();
	        }
	        else if(position == 1 ){
	        	setTitle("Rental");
	        	rentalFragment = new RentalFragment(1);
	        	getFragmentManager().beginTransaction().replace(R.id.content_frame, rentalFragment).commit();
	        }	        
	        else{
	        	setTitle("Rental");
	        	rentalFragment = new RentalFragment(2);
	        	getFragmentManager().beginTransaction().replace(R.id.content_frame, rentalFragment).commit();
	        }
	        
	        setTitle("hi");

	    }
	
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(navDrawerView);
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
		else if(id == android.R.id.home){
			Log.i("ajou", "click the up button");
			
			/*
			switch(beforeSelected){
				case -1 : getFragmentManager().beginTransaction().detach(mainFragment).commit();break;
				case 0 : getFragmentManager().beginTransaction().detach(profileFragment).commit();break;
				case 1: getFragmentManager().beginTransaction().detach(rentalFragment).commit(); break;
				case 2:break;
			}
			beforeSelected = -1;
			getFragmentManager().beginTransaction().attach(mainFragment).commit();
//			mDrawerList.setItemChecked(beforeSelected, true);
			 */
			mainFragment = new MainFragment();
			getFragmentManager().beginTransaction().replace(R.id.content_frame, mainFragment).commit();
			
			mDrawerLayout.closeDrawer(navDrawerView);
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	// nagivation drawer
	class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Log.i("ajou", "on drawerClick");
//			selectItem(position);
			FragmentManager fragmentManager = getFragmentManager();
			
			
			switch(beforeSelected){
			
			case -1 : fragmentManager.beginTransaction().detach(mainFragment).commit();break;
			case 0 : fragmentManager.beginTransaction().detach(profileFragment).commit();break;
			case 1: fragmentManager.beginTransaction().detach(rentalFragment).commit(); break;
			case 2:break;
			}
			beforeSelected = position;
			
			
			
			switch(position){
			case 0 : Log.i("ajou", "select the :"+ position+"");
				getFragmentManager().beginTransaction().attach( profileFragment).commit();
				break;
			case 1 : Log.i(TAG, "select the :"+ position+"");
				getFragmentManager().beginTransaction().attach(rentalFragment).commit();
				break; 
			case 2 : Log.i(TAG, "select the :"+ position+"");break;
			}

			// Highlight the selected item, update the title, and close the drawer
			mDrawerList.setItemChecked(position, true);
			setTitle(drawerNameList.get(position).getItemName());
			mDrawerLayout.closeDrawer(mDrawerList);
			
		}
	}
	
	

	

}
