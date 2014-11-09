package dk.app.AjouStartup.rental;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dk.app.AjouStartup.MyAdapter;
import dk.app.AjouStartup.R;

public class RentalFragment extends Fragment {

	private RecyclerView recycleView = null;
	private GridLayoutManager mLayoutManager;

	private View view = null;
	private int visibleThreshold = 2;
	int firstVisibleItem, visibleItemCount, totalItemCount, lastOne;
	private ArrayList<String> dataSet = new ArrayList<String>();
	RentalAdapter adapter ;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if (view == null) {
			view = inflater.inflate(R.layout.layout_rental_fragment, container,
					false);
			recycleView = (RecyclerView) view.findViewById(R.id.rental_fragment_recycler_view);
			recycleView.setHasFixedSize(true);
			recycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
				
				 @Override
				    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				        super.onScrolled(recyclerView, dx, dy);

				        visibleItemCount = recycleView.getChildCount();
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
			recycleView.setLayoutManager(mLayoutManager);
			
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
			
			adapter = new RentalAdapter(dataSet);
			recycleView.setAdapter(adapter);
			
		}
		/*
		else{
			((ViewGroup)view.getParent()).removeView(view);
		}
		*/
		

		return view;
	}

}
