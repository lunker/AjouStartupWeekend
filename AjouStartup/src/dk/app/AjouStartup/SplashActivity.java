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

public class SplashActivity extends Activity {

	private final String MAINPRODUCTNAME = "mp";
	private final String MAINPRODUCTEXTEN = ".jpeg";

	private final int SPLASH_DISPLAY_LENGTH = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash_activity);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				/* Create an Intent that will start the Menu-Activity. */
				Intent mainIntent = new Intent(SplashActivity.this,
						LoginActivity.class);
				SplashActivity.this.startActivity(mainIntent);
				SplashActivity.this.finish();
			}
		}, SPLASH_DISPLAY_LENGTH);

	}
}
