package dk.app.AjouStartup.rental;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dk.app.AjouStartup.MyAdapter;
import dk.app.AjouStartup.R;

public class RentalFragment extends Fragment {

	private RecyclerView recycleView = null;
	private RecyclerView.LayoutManager mLayoutManager;

	private View view = null;
	private String[] strDataSet = { "1", "2", "3", "4", "5", "6", "7" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if (view == null) {
			view = inflater.inflate(R.layout.layout_rental_fragment, container,
					false);
			recycleView = (RecyclerView) view.findViewById(R.id.rental_fragment_recycler_view);
			recycleView.setHasFixedSize(true);
			
			mLayoutManager = new GridLayoutManager(getActivity(), 2);
			recycleView.setLayoutManager(mLayoutManager);
			
//			MyAdapter adapter = new MyAdapter(strDataSet); 
			RentalAdapter adapter = new RentalAdapter();
			
			
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
