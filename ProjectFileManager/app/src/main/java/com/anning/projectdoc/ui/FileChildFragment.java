package com.anning.projectdoc.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anning.projectdoc.R;
import com.anning.projectdoc.lib.async.ZFileAsync;
import com.anning.projectdoc.lib.async.ZFileQWAsync;
import com.anning.projectdoc.lib.content.ZFileBean;
import com.anning.projectdoc.lib.content.ZFileContent;

import java.util.List;

public class FileChildFragment extends Fragment {

    public static FileChildFragment newInstance(String qwFileType) {
        Bundle bundle = new Bundle();
        bundle.putString(ZFileContent.QW_FILE_TYPE_KEY, qwFileType);
        FileChildFragment fragment = new FileChildFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_child, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String qwFileType = getArguments().getString(ZFileContent.QW_FILE_TYPE_KEY);
        AppCompatTextView text = view.findViewById(R.id.text);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);


        text.setText(qwFileType);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


//        ZFileQWAsync zFileQWAsync = new ZFileQWAsync(qwFileType, type, getContext(), new ZFileAsync.CallBack() {
//            @Override
//            public void invoke(List<ZFileBean> list) {
//                zfileQwBar.setVisibility(View.GONE);
//                if (list == null || list.isEmpty()) {
//                    qwAdapter.clear();
//                    zfileQwEmptyLayout.setVisibility(View.VISIBLE);
//                } else {
//                    qwAdapter.setDatas(list);
//                    zfileQwEmptyLayout.setVisibility(View.GONE);
//                }
//            }
//        });
//        zFileQWAsync.start(filterArray);
    }
}
