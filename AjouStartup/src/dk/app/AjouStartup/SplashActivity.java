package dk.app.AjouStartup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends Activity{


	private final String MAINPRODUCTNAME = "mp";
	private final String MAINPRODUCTEXTEN = ".jpeg";
	
	private final int SPLASH_DISPLAY_LENGTH = 1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash_activity);
		
		/*
		MainThread[] th = new MainThread[7];
		  for(int i = 0 ; i < 5 ; i++)
		  {
			  th[i] = new MainThread(i);
			  th[i].start();
		  }
		  */
		
		
		new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
		
		
	}
	
	class MainThread extends Thread {
		
		int i = 0 ;
		public static final int MAX_TOTAL_CONNECTION = 20;
		public static final int MAX_CONNECTIONS_PER_ROUTE = 20;
		public static final int TIMEOUT_CONNECT = 15000;
		public static final int TIMEOUT_READ = 15000;
		private HttpClient mHttpClient;
		HttpGet get =null;
		
		public MainThread(int i ){
			this.i = i ;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String SERVER = "http://192.168.43.137:8787";
			/*
			HttpGet get = new HttpGet(SERVER);
			try {
				
				get.addHeader("id",i+"");
				get.addHeader("state", "main");
				HttpResponse response = client.execute(get);
				Log.i("ajou", "get the response in main thread");
				Log.i("ajou", response.getHeaders("hi")[0].toString());
				
				Bitmap bitmap = BitmapFactory.decodeStream(response.getEntity().getContent());
//	    		Bitmap bitmap = GlobalVariable.decodeSampledBitmapFromFileInputStream(response.getEntity().getContent(), 80, 80);
				
				Log.i("ajou", getFilesDir().getAbsolutePath());
	    		File file = new File(getFilesDir().getAbsolutePath()+"/"+ MAINPRODUCTNAME+i+MAINPRODUCTEXTEN);
	    	
	    		FileOutputStream out = new FileOutputStream(file);
    			bitmap.compress(CompressFormat.JPEG, 100, out);
    			out.close();
    			Log.i("clientApp", "write image to cache");
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
			
			
			 SchemeRegistry schemeRegistry = new SchemeRegistry();
			   schemeRegistry.register(new Scheme( "http", PlainSocketFactory.getSocketFactory(), 80));

			   HttpParams params = new BasicHttpParams();
			   ConnManagerParams.setMaxTotalConnections(params, MAX_TOTAL_CONNECTION );
			   ConnManagerParams.setMaxConnectionsPerRoute( params, new ConnPerRouteBean(MAX_CONNECTIONS_PER_ROUTE) );  

			   HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONNECT);
			   HttpConnectionParams.setSoTimeout(params, TIMEOUT_READ);
			   HttpConnectionParams.setTcpNoDelay(params, true);

			   ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
			   
			   mHttpClient = new DefaultHttpClient(cm, null);
			   get = new HttpGet("http://192.168.43.137:8787");
			   	get.addHeader("id",i+"");
				get.addHeader("state", "main");
				HttpResponse response;
				try {
					response = mHttpClient.execute(get);
					Log.i("ajou", "get the response in main thread");
					Log.i("ajou", response.getHeaders("hi")[0].toString());
					
					Bitmap bitmap = BitmapFactory.decodeStream(response.getEntity().getContent());
//		    		Bitmap bitmap = GlobalVariable.decodeSampledBitmapFromFileInputStream(response.getEntity().getContent(), 80, 80);
					
					Log.i("ajou", getFilesDir().getAbsolutePath());
		    		File file = new File(getFilesDir().getAbsolutePath()+"/"+ MAINPRODUCTNAME+i+MAINPRODUCTEXTEN);
		    	
		    		FileOutputStream out = new FileOutputStream(file);
		    		bitmap.compress(CompressFormat.JPEG, 100, out);
	   			out.close();
	   			
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
		}//end run
	}
}
