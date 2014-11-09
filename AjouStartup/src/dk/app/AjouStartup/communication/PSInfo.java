package dk.app.AjouStartup.communication;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import dk.app.AjouStartup.R;

public class PSInfo extends Activity {

	private Typeface mTypeface;
	int userwant = -1;
	
	String[] results = null;
	ListView listview = null;
	
	ArrayAdapter<String> adapter = null;
	
	
	Handler mHandler = new Handler(){
	     public void handleMessage(Message msg) {
	      if(msg.what==0){
//	    	listview.setAdapter(new ArrayAdapter<String>(PSInfo.this,android.R.layout.simple_list_item_1, results));
//	    	adapter.addAll(results);
	    	adapter.addAll(results);
	    	adapter.notifyDataSetChanged();
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
	    
	    userwant =   getIntent().getExtras().getInt("position");
	    
	    ArrayList<String> tmp = new ArrayList<String>();

	    tmp.add("생각보다 별로에요.");
	    tmp.add("다음에 또 이용할게요");
	    tmp.add("잘입었습니다.");
	    tmp.add("옷에 문제가 있었어요");
	    
	    listview = (ListView) findViewById(R.id.ps_listView);
	    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tmp);
	    listview.setAdapter(adapter);
	    
//	    MyThread th = new MyThread(userwant, mHandler);
//	    th.start();
	    
	    Log.i("ajou", "start the ps thread");
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
	
	 class MyThread extends Thread{
		String SERVER = "http://192.168.43.137:8787";
	    	int position ; 
	    	Handler handler ; 
	    	public MyThread(int Position, Handler handler){
	    	this.position = position;	
	    	this.handler = handler;
	    	Log.i("ajou", "in thread, position :" + position);
	    	}
	    	
	    	@Override
	    	public void run() {
	    		// TODO Auto-generated method stub
	    		
	    		HttpClient client = new DefaultHttpClient();
	    		HttpGet get = new HttpGet(SERVER);
	    		get.addHeader("state","ps");
	    		get.addHeader("productid",position+"");
	    		
	    		try {
					HttpResponse response = client.execute(get);
					Log.i("ajou", "get the response,in ps");
					String tmp = EntityUtils.toString(response.getEntity() ,"UTF-16");
					
					String[] parts = tmp.split("%");
					Log.i("ajou", tmp.toString());
					results = parts;
					handler.sendEmptyMessage(0);
					
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}
	    }//end class
}
