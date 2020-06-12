package com.anningtex.library;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anningtex.library.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BaseMultiItemQuickAdapter
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/10 11:33
 */
public abstract class BaseMultiItemQuickAdapter<T extends MultiItemEntity> extends BaseQuickAdapter {
    protected List<T> mData;
    private SparseArray<Integer> layouts;

    public BaseMultiItemQuickAdapter(Context context, List<T> data) {
        super(context, data);
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.mContext = context;
    }

    @Override
    protected int getDefItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    @Override
    public BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        int layoutId = getLayoutId(viewType);
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        return new ContentViewHolder(view);
    }

    private int getLayoutId(int viewType) {
        return layouts.get(viewType);
    }

    protected void addItemType(int type, int layoutResId) {
        if (layouts == null) {
            layouts = new SparseArray<>();
        }
        layouts.put(type, layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        convert(helper, (T) item);
    }

    protected abstract void convert(BaseViewHolder helper, T item);
}
