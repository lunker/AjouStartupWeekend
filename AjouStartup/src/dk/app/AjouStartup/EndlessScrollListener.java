package dk.app.AjouStartup;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;


public class EndlessScrollListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    public EndlessScrollListener() {
    }

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    
    
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    	// TODO Auto-generated method stub
    	super.onScrolled(recyclerView, dx, dy);
    	
    	
    	Log.i("ajou", "onscrolled : " + dx + " , "+ dy );
    }
    
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    	// TODO Auto-generated method stub
    	
    	
    	super.onScrollStateChanged(recyclerView, newState);
    }
    
    
    
}

