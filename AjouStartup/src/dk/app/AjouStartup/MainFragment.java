package dk.app.AjouStartup;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

	private RecyclerView recyclerView = null;
	private MyAdapter adapter = null;
	private LinearLayoutManager layoutManager = null;

	private String[] strDataSet = { "1", "2", "3", "4", "5", "6", "7" };

	private View view = null;
	
	
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
			
		}
		else{
			((ViewGroup)view.getParent()).removeView(view);
		}
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		  layoutManager = new LinearLayoutManager(getActivity());
		  recyclerView.setLayoutManager(layoutManager);
		 
		  // specify an adapter (see also next example) 
		  adapter = new MyAdapter(strDataSet); 
		  recyclerView.setAdapter(adapter);
		
	}
	
	
}
