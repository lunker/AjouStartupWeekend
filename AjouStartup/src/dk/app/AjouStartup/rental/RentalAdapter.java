package dk.app.AjouStartup.rental;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import dk.app.AjouStartup.R;


public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.ViewHolder> {
	private String[] strDataSet;	
	int pos;
	ViewHolder vh = null;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
	
	int range = -1;
	
	Context t = null;
	ArrayList<String> dataset = new ArrayList<String>();
	public RentalAdapter(ArrayList<String> dataset, int range){
		this.dataset = dataset;
		this.range = range;
		
	}
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
		
		
        public CardView mCardView;
        public ImageView mImageView;
        public int size ; 
        public int mPosition;
        public Context pContext;
        
        public ViewHolder(View v, Context context) {
            super(v);
            mCardView = (CardView) v;
            mImageView = (ImageView) v.findViewById(R.id.rental_grid_item_image);
            pContext = context;
            
            final Context mContext = context;
            mCardView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("ajou", "on click in card view" + mPosition);

					Intent intent = new Intent(mContext, ProductInfo.class);
					intent.putExtra("position", mPosition);
					mContext.startActivity(intent);
				}
			});
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
  
    // Create new views (invoked by the layout manager)
    
    @Override
    public RentalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.layout_rental_grid_item, parent, false);
        final Context context = parent.getContext();
     
        
        // set the view's size, margins, paddings and layout parameters
        vh = new ViewHolder(v,  context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
    	
        if(position == (5 + range*2)){
//        	holder.mImageView.setImageBitmap(makeOverlay(holder.pContext, "/storage/emulated/0/DCIM/test/"+"mp"+ position+".jpeg", R.drawable.soldout));
        	holder.mImageView.setImageResource(R.drawable.soldout);
        }
        else{
        	holder.mImageView.setImageBitmap(BitmapFactory.decodeFile("/storage/emulated/0/DCIM/test/"+"mp"+ (position + range*3)+".jpeg"));
        }
        holder.mPosition = position;
        Log.i("ajou", "onbindview pos: " + position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }
	
    
    public Bitmap makeOverlay(Context context, String underImageSource, int resId){
    	
    	Bitmap bm1 = null;
    	Bitmap bm2 =  null;
    	Bitmap newBitmap = null;
    	  
    	  bm1 = BitmapFactory.decodeFile(underImageSource);
		   bm2 = BitmapFactory.decodeResource(context.getResources(), resId);
		   
  	  int w;
  	   if(bm1.getWidth() >= bm2.getWidth()){
		w = bm1.getWidth();
  	   }else{
		w = bm2.getWidth();
  	   }
  	   
  	   int h;
  	   if(bm1.getHeight() >= bm2.getHeight()){
		h = bm1.getHeight();
  	   }else{
		h = bm2.getHeight();
  	   }
  	   
  	   
  	   
  	  
  	   Config config = bm1.getConfig();
  	   if(config == null){
		config = Bitmap.Config.ARGB_8888;
  	   }
  	   
  	   newBitmap = Bitmap.createBitmap(w, h, config);
  	   Canvas newCanvas = new Canvas(newBitmap);
  	   
  	   newCanvas.drawBitmap(bm1, 0, 0, null);
  	   
  	   Paint paint = new Paint();
  	   paint.setAlpha(128);
  	   newCanvas.drawBitmap(bm2, 0, 0, paint);
    	  
    	  return newBitmap;
    }
}
