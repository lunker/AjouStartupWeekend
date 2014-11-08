package dk.app.AjouStartup.profile;

import dk.app.AjouStartup.R;
import dk.app.AjouStartup.R.layout;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment{

	
	
	private View view = null;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		if(view == null){
			
		
			view = inflater.inflate(R.layout.layout_profile_fragment, container, false);
		}
		
		
		
		
		return view;
	}
}
