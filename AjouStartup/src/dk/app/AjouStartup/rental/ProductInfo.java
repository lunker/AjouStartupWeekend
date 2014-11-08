package dk.app.AjouStartup.rental;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import dk.app.AjouStartup.R;

public class ProductInfo extends Activity {
	
	
//	Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Roboto-Medium.ttf");
	private TextView tv = null;
	private ImageView iv = null;
	private ImageView ivColor = null;
	
	
	
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
		
//		tv = (TextView) findViewById(R.id.product_name);
//		tv.setTypeface(typeface);
		
		

		int position = getIntent().getExtras().getInt("position");
		iv = (ImageView) findViewById(R.id.product_image);
		iv.setImageBitmap(BitmapFactory.decodeFile("/data/data/dk.app.AjouStartup/files/"+"mp"+ position+".jpeg"));
		
		ivColor = (ImageView) findViewById(R.id.product_info_color);
		ivColor.setBackgroundColor(Color.parseColor("#303030"));
		
		
	}
}
