package com.anning.projectdoc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.anning.projectdoc.R;

import java.util.List;

public class FileTypeAdapter extends RecyclerView.Adapter<FileTypeAdapter.ViewHolder> {
    private List<String> data;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvFileTypeName.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<String> fileTypeList) {
        data = fileTypeList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvFileTypeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFileTypeName = itemView.findViewById(R.id.tv_file_type_name);
        }
    }
}
