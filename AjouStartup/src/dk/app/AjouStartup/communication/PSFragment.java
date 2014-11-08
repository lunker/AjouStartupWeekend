package dk.app.AjouStartup.communication;

import dk.app.AjouStartup.R;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PSFragment extends Fragment {

	
	private View view = null;
	private RecyclerView recyclerView = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		if(view == null){
			view = inflater.inflate(R.layout.layout_ps_fragment, container,false);
			recyclerView = (RecyclerView) view.findViewById(R.id.ps_fragment_recycler_view);
			
			
			

			
			
			
		}
		

		return view;
	}
	
	
	
}
