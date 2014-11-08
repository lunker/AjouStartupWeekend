package dk.app.AjouStartup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

	
	private RecyclerView recyclerView = null;
	private MyAdapter adapter = null;
	private GridLayoutManager layoutManager = null;

	private String[] strDataSet = { "1", "2", "3", "4", "5", "6", "7" };

	private View view = null;
	private final String MAINPRODUCTNAME = "mtest";
	private final String MAINPRODUCTEXTEN = ".PNG";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		if( view == null){
			view =  inflater.inflate(R.layout.layout_main_fragment, container, false);
			
			  // use this setting to improve performance if you know that changes
			  // in content do not change the layout size of the RecyclerView
			  recyclerView = (RecyclerView)view.findViewById(R.id.main_activity_recycle_view);
			  recyclerView.setHasFixedSize(true);
			  
//			  MainThread[] th = new MainThread[5];
//			  for(int i = 0 ; i < 5 ; i++)
//			  {
//				  
//				  th[i] = new MainThread(i);
//				  th[i].start();
//			  }
		}
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		  layoutManager = new GridLayoutManager(getActivity(), 1);
		  recyclerView.setLayoutManager(layoutManager);
		 
		  // specify an adapter (see also next example) 
		  adapter = new MyAdapter(strDataSet); 
		  recyclerView.setAdapter(adapter);
		
	}
	
	
}
