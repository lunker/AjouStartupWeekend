package dk.app.AjouStartup;

import com.google.android.gms.internal.pa;

import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
  
	
	
	GlobalVariable global = null;
	
	
//	private 
	private String[] strDataSet;	
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
		
		
        public CardView mCardView;
        public TextView mTextView;
        public ImageView mImageView;
        
        
        public ViewHolder(View v) {
            super(v);
            
            mCardView = (CardView) v;
            mTextView  = (TextView) v.findViewById(R.id.main_card_view_text);
            mImageView = (ImageView) v.findViewById(R.id.main_card_view_image);
            
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        strDataSet = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
    	global = (GlobalVariable ) parent.getContext().getApplicationContext();
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.layout_main_activity_card_view, parent, false);
        
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.mTextView.setText(strDataSet[position]);
        holder.mImageView.setImageBitmap(BitmapFactory.decodeFile("/data/data/dk.app.AjouStartup/files/"+"mp"+ position+".jpeg"));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return strDataSet.length;
    }
}


