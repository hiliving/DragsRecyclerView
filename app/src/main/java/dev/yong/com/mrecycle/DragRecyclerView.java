package dev.yong.com.mrecycle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collections;
import java.util.List;


/**
 * Created by HY on 2017/9/28.
 */

public class DragRecyclerView<T> extends RecyclerView {
    /**
     * 数据集合
     */
    private List mData;

    /**
     * 拖动中背景的颜色
     */
    private int DragingBgColor;

    public DragRecyclerView(Context context) {
        this(context,null);
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragRecyclerView);
        DragingBgColor = typedArray.getColor(R.styleable.DragRecyclerView_dragingColor,Color.GRAY);
        typedArray.recycle();
        init();
    }

    private void init() {
        helper.attachToRecyclerView(this);
    }
    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //首先回调的方法 返回int表示是否监听该方向
            int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;//拖拽
            int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//侧滑删除
            return makeMovementFlags(dragFlags,swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //滑动事件
            Collections.swap(mData,viewHolder.getAdapterPosition(),target.getAdapterPosition());
            getAdapter().notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //侧滑事件
            mData.remove(viewHolder.getAdapterPosition());
            getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
        }
        @Override
        public boolean isLongPressDragEnabled() {

            return true;
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
                //侧滑或拖动时背景设为灰色
                viewHolder.itemView.setBackgroundColor(DragingBgColor);
            }

        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //正常状态下背景恢复默认
            viewHolder.itemView.setBackgroundColor(0);
        }
    });

    public void setAdapter(Adapter adapter, List<T> mDatas) {
        super.setAdapter(adapter);
        this.mData = mDatas;
    }
}
