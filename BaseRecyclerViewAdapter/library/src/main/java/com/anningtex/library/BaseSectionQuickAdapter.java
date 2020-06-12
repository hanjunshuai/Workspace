package com.anningtex.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.anningtex.library.entity.SectionEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BaseSectionQuickAdapter
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 17:41
 */
public abstract class BaseSectionQuickAdapter<T extends SectionEntity> extends BaseQuickAdapter {
    protected int sectionHeadResId;
    protected List<T> data;
    protected static final int SECTION_HEADER_VIEW = 0x00000004;

    public BaseSectionQuickAdapter(Context context, int layoutResId, int sectionHeadResId, List data) {
        super(context, layoutResId, data);
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.sectionHeadResId = sectionHeadResId;
    }

    @Override
    public BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        View item = null;
        if (viewType == SECTION_HEADER_VIEW) {
            item = LayoutInflater.from(parent.getContext()).inflate(
                    sectionHeadResId, parent, false);
            return new SectionHeadViewHolder(item);
        } else {
            item = LayoutInflater.from(parent.getContext()).inflate(
                    mLayoutResId, parent, false);
            return new BaseViewHolder(mContext, item);
        }
    }


    @Override
    public int getDefItemViewType(int position) {
        return data.get(position).isHeader ? SECTION_HEADER_VIEW : 0;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        convert(helper, (T) item);
    }

    @Override
    public void onBindDefViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        int type = getItemViewType(position);
        if (type == SECTION_HEADER_VIEW) {
            int index = position - getHeaderViewsCount();
            convertHead(baseViewHolder, data.get(index));
            if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
        }
    }

    protected abstract void convertHead(BaseViewHolder helper, T item);

    protected abstract void convert(BaseViewHolder helper, T item);

    public class SectionHeadViewHolder extends BaseViewHolder {

        public SectionHeadViewHolder(View itemView) {
            super(itemView.getContext(), itemView);
        }
    }
}
