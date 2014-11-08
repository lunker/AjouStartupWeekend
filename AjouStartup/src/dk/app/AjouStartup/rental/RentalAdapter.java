package dk.app.AjouStartup.rental;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.sax.StartElementListener;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import dk.app.AjouStartup.R;


public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.ViewHolder> {
	private String[] strDataSet;	
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
		
		
        public CardView mCardView;
        public ImageView mImageView;
        public int size ; 
        
        public ViewHolder(View v) {
            super(v);
            mCardView = (CardView) v;
            mImageView = (ImageView) v.findViewById(R.id.rental_grid_item_image);
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
        v.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(context, ProductInfo.class);
				context.startActivity(intent);
			}
		});
        
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        
        holder.mImageView.setImageBitmap(BitmapFactory.decodeFile("/data/data/dk.app.AjouStartup/files/"+"mp"+ position+".jpeg"));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 10;
    }
	
}
