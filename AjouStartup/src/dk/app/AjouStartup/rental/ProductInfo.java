package dk.app.AjouStartup.rental;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import dk.app.AjouStartup.R;
import dk.app.AjouStartup.communication.PSInfo;

public class ProductInfo extends Activity {
	
	
//	Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Roboto-Medium.ttf");
	private TextView tv = null;
	private ImageView iv = null;
	private View ivColor = null;
	private int position = -1;
	private Typeface mTypeface;
	
	private TextView rentalFrom;
	private TextView rentalTo;
	private TextView rentalPrice;
	private TextView rentalPS;
	
	
	private Button btn_rental;
	private String from = ""; 
	private String to = "";
	private String price = "";
	private String ps = "";
//	

	Handler mHandler = new Handler(){
	     public void handleMessage(Message msg) {
	      if(msg.what==0){
	    	  rentalFrom.setText(from);
	    	  rentalTo.setText(to);
	    	  rentalPS.setText(ps);
	    	  rentalPrice.setText(price);
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
		setContentView(R.layout.layout_product_info);
		
		mTypeface = Typeface.createFromAsset(getAssets(), "robotobold.ttf.mp3");
	    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
	    setGlobalFont(root);
	    
		position = getIntent().getExtras().getInt("position");
		iv = (ImageView) findViewById(R.id.product_image);
		iv.setImageBitmap(BitmapFactory.decodeFile("/storage/emulated/0/DCIM/test/"+"mp"+ position+".jpeg"));
		
		ivColor = (View) findViewById(R.id.myRectangleView);
		ivColor.setBackgroundColor(Color.parseColor("#303030"));
		
		
		btn_rental = (Button) findViewById(R.id.product_info_btn_rental);
		btn_rental.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

			
			}
		});
		MyThread th = new MyThread(mHandler);
		th.start();
		
		rentalFrom = (TextView) findViewById(R.id.product_info_rental_date_from);
		rentalTo = (TextView) findViewById(R.id.product_info_rental_date_to);
		rentalPrice = (TextView) findViewById(R.id.product_info_price);
		rentalPS = (TextView) findViewById(R.id.product_info_ps_content);
		
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
		Handler handler = null;
		
		public MyThread(Handler handler){
			this.handler = handler; 
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(SERVER);
			
			try {
				get.addHeader("state", "getdetail");
				get.addHeader("productid", position+"");
				HttpResponse response = client.execute(get);
				
				for(int i = 0 ; i < response.getAllHeaders().length;i++)
					Log.i("ajou", "test the thread : get the respon" + response.getAllHeaders()[i].toString());
				
				from = (response.getHeaders("rentalFrom"))[0].getValue();
				to = (response.getHeaders("rentalTo"))[0].getValue();
//				ps = new String( response.getHeaders("ps")[0].getValue().getBytes(), "UTF-16");
				ps = EntityUtils.toString(response.getEntity() ,"UTF-16");
				
				price =( response.getHeaders("rentalPrice")[0]).getValue();
				handler.sendEmptyMessage(0);
				
				Log.i("ajou", "test the thread : " + ps);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
	

