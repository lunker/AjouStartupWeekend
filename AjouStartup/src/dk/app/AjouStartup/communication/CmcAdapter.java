package dk.app.AjouStartup.communication;

import java.util.ArrayList;

import com.google.android.gms.internal.mp;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dk.app.AjouStartup.R;
import dk.app.AjouStartup.rental.ProductInfo;


public class CmcAdapter extends RecyclerView.Adapter<CmcAdapter.ViewHolder> {
	private String[] strDataSet;	
	int pos;
	ViewHolder vh = null;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
	
	ArrayList<String> dataset = new ArrayList<String>();
	public CmcAdapter(ArrayList<String> dataset){
		this.dataset = dataset;
	}
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
		
		
        public CardView mCardView;
        public ImageView productImage;
        public ImageView likeImage;
        public ImageView hateImage;
        public TextView likeCount;
        public TextView hateCount;
        public int likeCounter = 0;
        public int hateCounter = 0;
        public int mPosition;
        
        public ViewHolder(View v, Context context) {
            super(v);
            mCardView = (CardView) v;
            productImage = (ImageView) v.findViewById(R.id.cmc_grid_item_image);
            likeImage = (ImageView) v.findViewById(R.id.cmc_sns_like);
            likeImage.setImageResource(R.drawable.like);
            likeImage.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					likeCounter++;
					likeCount.setText(likeCounter+"");
				}
			});
            likeCount = (TextView) v.findViewById(R.id.cmc_sns_like_count);
            hateImage = (ImageView) v.findViewById(R.id.cmc_sns_hate);
            hateImage.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					hateCounter++;
					hateCount.setText(hateCounter+"");
				}
			});
            hateImage.setImageResource(R.drawable.hate);
            hateCount = (TextView) v.findViewById(R.id.cmc_sns_hate_count);
            
            final Context mContext = context;
            
            mCardView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("ajou", "on click in card view" + mPosition);
					
					
					
					Intent intent = new Intent(mContext, PSInfo.class);
					intent.putExtra("position", mPosition);
					mContext.startActivity(intent);
					
					
//					finish();
					
				}
			});
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
  
    // Create new views (invoked by the layout manager)
    
    @Override
    public CmcAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.layout_cmc_fragment_card_item, parent, false);
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
        
        holder.productImage.setImageBitmap(BitmapFactory.decodeFile("/storage/emulated/0/DCIM/test/"+"mp"+ position+".jpeg"));
        holder.mPosition = position;
        Log.i("ajou", "onbindview pos: " + position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }
    
   
	
}