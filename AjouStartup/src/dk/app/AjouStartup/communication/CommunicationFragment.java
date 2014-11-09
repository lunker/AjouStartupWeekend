package dk.app.AjouStartup.communication;

import java.util.ArrayList;

import dk.app.AjouStartup.R;
import dk.app.AjouStartup.rental.RentalAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommunicationFragment extends Fragment{

	
	
	private View view = null;
	private RecyclerView recyclerView = null;
	
	private GridLayoutManager mLayoutManager;

	private int visibleThreshold = 2;
	int firstVisibleItem, visibleItemCount, totalItemCount, lastOne;
	private ArrayList<String> dataSet = new ArrayList<String>();
	CmcAdapter adapter ;


	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		
		if(view == null){
			view = inflater.inflate(R.layout.layout_cmc_fragment_layout, container,false);
			
			recyclerView = (RecyclerView) view.findViewById(R.id.cmc_fragment_recycler_view);
			recyclerView.setHasFixedSize(true);
			recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
				
				 @Override
				    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				        super.onScrolled(recyclerView, dx, dy);

				        visibleItemCount = recyclerView.getChildCount();
//				        adapter.g
				        lastOne = mLayoutManager.findLastVisibleItemPosition();
				        
			                if ( (visibleItemCount - lastOne) <= visibleThreshold) {
			                    
			                	dataSet.add("1");
			                	
			                    adapter.notifyDataSetChanged();
			                    adapter.notifyItemRangeInserted(0, dataSet.size());
			                    Log.i("ajou", "at end. . .");
			                }
				    }
			});
			mLayoutManager = new GridLayoutManager(getActivity(), 2);
			recyclerView.setLayoutManager(mLayoutManager);
			
//			MyAdapter adapter = new MyAdapter(strDataSet); 
			
			dataSet.add("1");
			dataSet.add("1");
			dataSet.add("1");
			dataSet.add("1");
			dataSet.add("1");
			dataSet.add("1");
			dataSet.add("1");
			dataSet.add("1");
			dataSet.add("1");
			
			adapter = new CmcAdapter(dataSet);
			recyclerView.setAdapter(adapter);
		}
		
		
		
		return view; 
	}
	
	
}
