package me.danielaguilar.recepies;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by danielaguilar on 18-03-18.
 */

public class StepsLayoutManager extends LinearLayoutManager {
    public interface OnItemsAddedListener{
        void onItemsAttached();
    }

    private OnItemsAddedListener listener;

    public StepsLayoutManager(Context context) {
        super(context);
    }

    public StepsLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public StepsLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setListener(OnItemsAddedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        if(listener!=null){
            listener.onItemsAttached();
        }
        super.onLayoutCompleted(state);
    }

    public void setAllAsUnselected(){
        for (int i = 0; i< getChildCount(); i++){
            findViewByPosition(i).setSelected(false);
        }
    }
}
