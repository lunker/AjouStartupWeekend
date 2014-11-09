package dk.app.AjouStartup.communication;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import dk.app.AjouStartup.R;

public class PSInfo extends Activity {

	private Typeface mTypeface;
	
	Handler mHandler = new Handler(){
	     public void handleMessage(Message msg) {
	      if(msg.what==0){
	    	
	      }
	};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
		layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		layoutParams.dimAmount = 0.7f;
		getWindow().setAttributes(layoutParams);
		setContentView(R.layout.layout_ps_info);
		
		mTypeface = Typeface.createFromAsset(getAssets(), "robotobold.ttf.mp3");
	    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
	    setGlobalFont(root);
	    
	    
	    
	}
	
	void setGlobalFont(ViewGroup root) {
	    
		for (int i = 0; i < root.getChildCount(); i++) {
	        View child = root.getChildAt(i);
	        if (child instanceof TextView)
	            ((TextView)child).setTypeface(mTypeface,Typeface.BOLD);
	        else if (child instanceof ViewGroup)
	            setGlobalFont((ViewGroup)child);
	    }
	}
}
