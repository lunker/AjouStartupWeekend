package dk.app.AjouStartup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import dk.app.AjouStartup.profile.ProfileFragment;
import dk.app.AjouStartup.rental.RentalFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class MainActivity extends ActionBarActivity {

	/*
	 * drawer
	 */
	private final String MAINPRODUCTNAME = "mtest";
	private final String MAINPRODUCTEXTEN = ".jpg";
	
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

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
//	String[] subCategorys = {"christmas", "wedding"};
	private List<String> mainCategory = null;
	private HashMap<String, List<String>> subCategory = new HashMap<String, List<String>>();
	private List<String> rentalCategroy = new ArrayList<String>();
//	private List<String> subCategory = null;
	
	
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
	
		Log.i("ajou",getpixels(65)+"");
//		MainThread th = new MainThread();
//		th.start();
		
		Log.i("ajou", "asdf");
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		drawerNameList = new ArrayList<DrawerItem>();
		drawerNameList.add(new DrawerItem("profile",true));
		drawerNameList.add(new DrawerItem("Rental Service"));
		drawerNameList.add(new DrawerItem("Communication"));
		
		drawerAdapter = new MyDrawerAdapter(this, drawerNameList);
		
		// Set the adapter for the list view
		
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//		mDrawerList.setAdapter( drawerAdapter);
//		mDrawerList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, categorys));
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.test_test, categorys));
		
		mainCategory = new ArrayList<String>();
		mainCategory.add("profile");
		mainCategory.add("rental");
		mainCategory.add("communication");
		
		rentalCategroy.add("wedding");
		rentalCategroy.add("travle");
		
		subCategory.put("profile", new ArrayList<String>());
		subCategory.put("rental", rentalCategroy);
		subCategory.put("communication", new ArrayList<String>());
		
		  MainThread[] th = new MainThread[4];
		  for(int i = 0 ; i < 4 ; i++)
		  {
			  
			  th[i] = new MainThread(i);
			  th[i].start();
		  }
//		mDrawerList.setAdapter(new MyExpandableAdapter(this, mainCategory, subCategory));
//		mDrawerList.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
		/*
		mDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
	        @Override
	        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
	        	Log.i("ajou", "on drawerClick");
//				selectItem(position);
	        	
				FragmentManager fragmentManager = getFragmentManager();
				
				switch(beforeSelected){
				
				case -1 : fragmentManager.beginTransaction().detach(mainFragment).commit();break;
				case 0 : fragmentManager.beginTransaction().detach(profileFragment).commit();break;
				case 1: fragmentManager.beginTransaction().detach(rentalFragment).commit(); break;
				case 2:break;
				}
				beforeSelected = groupPosition;
				
				
				switch(groupPosition){
				case 0 : Log.i("ajou", "select the :"+ groupPosition+"");
					getFragmentManager().beginTransaction().attach( profileFragment).commit();
					break;
				case 1 : Log.i(TAG, "select the :"+ groupPosition+"");
					getFragmentManager().beginTransaction().attach(rentalFragment).commit();
					break; 
				case 2 : Log.i(TAG, "select the :"+ groupPosition+"");break;
				}

				// Highlight the selected item, update the title, and close the drawer
				mDrawerList.setItemChecked(groupPosition, true);
				setTitle(drawerNameList.get(groupPosition).getItemName());
				mDrawerLayout.closeDrawer(mDrawerList);
	        
				return true;
	        }
	    });
	    */
		
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
		else if(id == android.R.id.home){
			Log.i("ajou", "click the up button");
			
			switch(beforeSelected){
			
				case -1 : getFragmentManager().beginTransaction().detach(mainFragment).commit();break;
				case 0 : getFragmentManager().beginTransaction().detach(profileFragment).commit();break;
				case 1: getFragmentManager().beginTransaction().detach(rentalFragment).commit(); break;
				case 2:break;
			
			}
			beforeSelected = -1;
			getFragmentManager().beginTransaction().attach(mainFragment).commit();
			mDrawerList.setItemChecked(beforeSelected, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			
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
	
	class MainThread extends Thread {
		
		int i = 0 ;
		
		public MainThread(int i ){
			this.i = i ;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			  String SERVER = "http://192.168.43.137:8787";
//			GlobalVariable global = (GlobalVariable)getActivity();
			
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(SERVER);
			
			try {
				get.addHeader("id",i+"");
				get.addHeader("state", "main");
				HttpResponse response = client.execute(get);
				Log.i("ajou", "get the response in main thread");
				Log.i("ajou", response.getHeaders("hi")[0].toString());
				
				
				Bitmap bitmap = BitmapFactory.decodeStream(response.getEntity().getContent());
//	    		Bitmap bitmap = GlobalVariable.decodeSampledBitmapFromFileInputStream(response.getEntity().getContent(), 80, 80);
				
				Log.i("ajou", getCacheDir().getAbsolutePath());
	    		File file = new File(getCacheDir().getAbsolutePath()+"/"+ MAINPRODUCTNAME+i+MAINPRODUCTEXTEN);
	    		/*
	    		if( !file.exists()){
	    			file.createNewFile();
	    			FileOutputStream out = new FileOutputStream(file);
	    			bitmap.compress(CompressFormat.PNG, 100, out);
	    			out.close();
	    			Log.i("clientApp", "write image to cache");
	    		}
	    		else{
	    			FileOutputStream out = new FileOutputStream(file);
	    			bitmap.compress(CompressFormat.PNG, 100, out);
	    			out.close();
	    			Log.i("clientApp", "write image to cache");
	    		}
				
				*/
	    		FileOutputStream out = new FileOutputStream(file);
    			bitmap.compress(CompressFormat.JPEG, 100, out);
    			out.close();
    			Log.i("clientApp", "write image to cache");
	    		
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
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
