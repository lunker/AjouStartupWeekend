package dk.app.AjouStartup.rental;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import dk.app.AjouStartup.R;

public class ProductInfo extends Activity {
	
	
//	Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Roboto-Medium.ttf");
	private TextView tv = null;
	
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
		
		
		
		
	}
}
