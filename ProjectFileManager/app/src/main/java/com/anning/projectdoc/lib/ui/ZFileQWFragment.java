package com.anning.projectdoc.lib.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anning.projectdoc.R;
import com.anning.projectdoc.lib.adapter.ZFileListAdapter;
import com.anning.projectdoc.lib.async.ZFileAsync;
import com.anning.projectdoc.lib.async.ZFileQWAsync;
import com.anning.projectdoc.lib.common.ZFileFragment;
import com.anning.projectdoc.lib.content.ZFileBean;
import com.anning.projectdoc.lib.content.ZFileConfiguration;
import com.anning.projectdoc.lib.content.ZFileContent;
import com.anning.projectdoc.lib.listener.ZFileListener;
import com.anning.projectdoc.lib.util.ZFileUtil;

import java.util.List;
import java.util.Objects;

public class ZFileQWFragment extends ZFileFragment {

    private String qwFileType = ZFileConfiguration.QQ;
    // 文件类型
    private int type = ZFileContent.ZFILE_QW_PIC;
    private boolean qwManage = false;

    private ZFileListAdapter qwAdapter = null;
    private RecyclerView zfileQwRecyclerView;
    private LinearLayout zfileQwBar;
    private FrameLayout zfileQwEmptyLayout;
    private ImageView zfileQwEmptyPic;

    public static ZFileQWFragment newInstance(String qwFileType, int type, boolean isManager) {
        Bundle bundle = new Bundle();
        bundle.putString(ZFileContent.QW_FILE_TYPE_KEY, qwFileType);
        bundle.putInt("type", type);
        bundle.putBoolean("isManager", isManager);
        ZFileQWFragment fragment = new ZFileQWFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_zfile_qw;
    }

    @Override
    public void initAll() {
        qwFileType = getArguments().getString(ZFileContent.QW_FILE_TYPE_KEY);
        if (TextUtils.isEmpty(qwFileType)) {
            qwFileType = ZFileConfiguration.QQ;
        }
        type = getArguments().getInt("type");
        if (type == 0) {
            type = ZFileContent.ZFILE_QW_PIC;
        }
        initView();
        initRecyclerView();
    }

    private void initView() {
        zfileQwRecyclerView = view.findViewById(R.id.zfile_qw_recyclerView);
        zfileQwBar = view.findViewById(R.id.zfile_qw_bar);
        zfileQwEmptyLayout = view.findViewById(R.id.zfile_qw_emptyLayout);
        zfileQwEmptyPic = view.findViewById(R.id.zfile_qw_emptyPic);
    }

    private void initRecyclerView() {
        initAdapter();
        zfileQwEmptyPic.setImageResource(ZFileContent.getEmptyRes());
        zfileQwRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        zfileQwRecyclerView.setAdapter(qwAdapter);
        zfileQwBar.setVisibility(View.VISIBLE);

        ZFileListener.QWFileLoadListener qwFileLoadListener = ZFileContent.getZFileHelp().getQWFileLoadListener();
        String[] filterArray;
        if (qwFileLoadListener != null) {
            filterArray = qwFileLoadListener.getFilterArray(type);
        } else {
            filterArray = ZFileContent.getFilterArray(type);
        }


        ZFileQWAsync zFileQWAsync = new ZFileQWAsync(qwFileType, type, getContext(), new ZFileAsync.CallBack() {
            @Override
            public void invoke(List<ZFileBean> list) {
                zfileQwBar.setVisibility(View.GONE);
                if (list == null || list.isEmpty()) {
                    qwAdapter.clear();
                    zfileQwEmptyLayout.setVisibility(View.VISIBLE);
                } else {
                    qwAdapter.setDatas(list);
                    zfileQwEmptyLayout.setVisibility(View.GONE);
                }
            }
        });
        zFileQWAsync.start(filterArray);
    }

    private void initAdapter() {
        if (qwAdapter == null) {
            qwAdapter = new ZFileListAdapter(getContext(), true);
            qwAdapter.setItemClickByAnim(new ZFileListAdapter.ItemClickByAnim() {
                @Override
                public void onClick(View view, int position, ZFileBean bean) {
                    ZFileUtil.openFile(bean.getFilePath(), view);
                }
            });
            qwAdapter.setQwListener(new ZFileListAdapter.QWListener() {
                @Override
                public void invoke(boolean isManage, ZFileBean bean, boolean isSelect) {
                    if (isManage) {
                        ((ZFileQWActivity) requireActivity()).observer(ZFileContent.toQWBean(bean, isSelect));
                    }
                }
            });
            qwAdapter.setManage(qwManage);
        }
    }

    public void setManager(boolean isManage) {
        if (this.qwManage != isManage) {
            this.qwManage = isManage;
            if (qwAdapter != null) {
                qwAdapter.setManage(isManage);
            }
        }
    }

    public void removeLastSelectData(ZFileBean bean) {
        if (qwAdapter != null) {
            qwAdapter.setQWLastState(bean);
        }
    }

    public void resetAll() {
        qwManage = false;
        if (qwAdapter != null) {
            qwAdapter.setManage(false);
        }
    }
}
