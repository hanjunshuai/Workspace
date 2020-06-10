package com.anningtex.library;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;


import androidx.annotation.IntDef;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.anningtex.library.animation.AlphaInAnimation;
import com.anningtex.library.animation.BaseAnimation;
import com.anningtex.library.animation.ScaleInAnimation;
import com.anningtex.library.animation.SlideInBottomAnimation;
import com.anningtex.library.animation.SlideInLeftAnimation;
import com.anningtex.library.animation.SlideInRightAnimation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BaseQuickAdapter
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 11:17
 */
public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected static final String TAG = BaseQuickAdapter.class.getSimpleName();

    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    public static final int ALPHAIN = 0;
    public static final int SCALEIN = 1;
    public static final int SLIDEIN_BOTTOM = 2;
    public static final int SLIDEIN_LEFT = 3;
    public static final int SLIDEIN_RIGHT = 4;

    protected Context context;
    protected int mLayoutResId;
    protected List<T> mData;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private int mLastPosition = -1;
    private boolean isFirstOnly = true;
    @AnimationType
    private int animationType = ALPHAIN;
    private BaseAnimation customAnimation = null;
    private BaseAnimation selectAnimation = new AlphaInAnimation();
    private boolean isOpenAnimation = false;

    private static final int HEADER_VIEW = 0x00000001;
    private static final int LOADING_VIEW = 0x00000002;
    private static final int FOOTER_VIEW = 0x00000003;
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    private boolean mNextLoad;
    private boolean mIsLoadingMore;

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private RequestLoadMoreListener mRequestLoadMoreListener;

    public void setLoadMoreListener(int pageSize, RequestLoadMoreListener requestLoadMoreListener) {
        if (getItemCount() < pageSize) {
            return;
        }
        mNextLoad = true;
        this.mRequestLoadMoreListener = requestLoadMoreListener;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    /**
     * Create a QuickAdapter.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     */
    public BaseQuickAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public BaseQuickAdapter(Context context, int layoutResId, List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.context = context;
        this.mLayoutResId = layoutResId;
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);

    }

    public void add(int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public List getData() {
        return mData;
    }

    public void addHeaderView(View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        if (mHeaderViews.size() == 0) {
            mHeaderViews.add(header);
        }
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        mNextLoad = false;
        if (footer == null) {
            throw new RuntimeException("footer is null");
        }
        if (mFooterViews.size() == 0) {
            mFooterViews.add(footer);
        }
        this.notifyDataSetChanged();
    }

    public void isNextLoad(boolean isNextLoad) {
        this.mNextLoad = isNextLoad;
        mIsLoadingMore = false;
        notifyDataSetChanged();
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    @Override
    public int getItemCount() {
        int i = mNextLoad ? 1 : 0;
        return mData.size() + i + getHeaderViewsCount() + getFooterViewsCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getHeaderViewsCount()) {
            return HEADER_VIEW;
        } else if (position == mData.size() + getHeaderViewsCount()) {
            if (mNextLoad) {
                return LOADING_VIEW;
            } else {
                return FOOTER_VIEW;
            }
        }
        return getDefItemViewType(position);
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = null;
        if (viewType == 0) {
            return onCreateDefViewHolder(parent, viewType);
        } else if (viewType == LOADING_VIEW) {
            return new FooterViewHolder(getItemView(R.layout.def_loading, parent));
        } else if (viewType == HEADER_VIEW) {
            return new HeadViewHolder(mHeaderViews.get(0));
        } else if (viewType == FOOTER_VIEW) {
            return new FooterViewHolder(mFooterViews.get(0));
        } else {
            return onCreateDefViewHolder(parent, viewType);
        }
    }

    protected View getItemView(int layoutResId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(
                layoutResId, parent, false);
    }

    public BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(getItemView(mLayoutResId, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ContentViewHolder) {
            int index = position - getHeaderViewsCount();
            BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
            convert(baseViewHolder, mData.get(index));
            if (onRecyclerViewItemClickListener != null) {
                baseViewHolder.itemView.setOnClickListener(v -> onRecyclerViewItemClickListener.onItemClick(v, position - getHeaderViewsCount()));
            }
            if (isOpenAnimation) {
                if (!isFirstOnly || position > mLastPosition) {
                    BaseAnimation animation = null;
                    if (customAnimation != null) {
                        animation = customAnimation;
                    } else {
                        animation = selectAnimation;
                    }
                    for (Animator anim : animation.getAnimators(holder.itemView)) {
                        anim.setDuration(mDuration).start();
                        anim.setInterpolator(mInterpolator);
                    }
                    mLastPosition = position;
                }
            }
        } else if (holder instanceof FooterViewHolder) {
            if (mNextLoad && !mIsLoadingMore && mRequestLoadMoreListener != null) {
                mIsLoadingMore = true;
                mRequestLoadMoreListener.onLoadMoreRequested();
                if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    params.setFullSpan(true);
                }
            }

        } else if (holder instanceof HeadViewHolder) {

        } else {
            int index = position - getHeaderViewsCount();
            BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
            onBindDefViewHolder(baseViewHolder, index);
        }
    }

    public void openLoadAnimation(@AnimationType int animationType) {
        this.isOpenAnimation = true;
        this.customAnimation = null;
        switch (animationType) {
            case ALPHAIN:
                selectAnimation = new AlphaInAnimation();
                break;
            case SCALEIN:
                selectAnimation = new ScaleInAnimation();
                break;
            case SLIDEIN_BOTTOM:
                selectAnimation = new SlideInBottomAnimation();
                break;
            case SLIDEIN_LEFT:
                selectAnimation = new SlideInLeftAnimation();
                break;
            case SLIDEIN_RIGHT:
                selectAnimation = new SlideInRightAnimation();
                break;
        }
    }

    public void openLoadAnimation(BaseAnimation animation) {
        this.isOpenAnimation = true;
        this.customAnimation = animation;
    }

    public void openLoadAnimation() {
        this.isOpenAnimation = true;
    }

    public void setFirstOnly(boolean firstOnly) {
        this.isFirstOnly = firstOnly;
    }

    /**
     * 绑定视图
     *
     * @param helper
     * @param item
     */
    protected abstract void convert(BaseViewHolder helper, T item);

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void onBindDefViewHolder(RecyclerView.ViewHolder holder, final int position) {

    }

    public static class FooterViewHolder extends BaseViewHolder {

        protected FooterViewHolder(View view) {
            super(view.getContext(), view);
        }
    }

    public static class HeadViewHolder extends BaseViewHolder {

        protected HeadViewHolder(View view) {
            super(view.getContext(), view);
        }
    }

    public static class ContentViewHolder extends BaseViewHolder {
        public ContentViewHolder(View itemView) {
            super(itemView.getContext(), itemView);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        public void onItemClick(View view, int position);
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }
}
