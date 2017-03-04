package de.jonasrottmann.realmbrowser.files.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.jonasrottmann.realmbrowser.files.model.FilesPojo;
import java.util.ArrayList;
import java.util.List;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {

    private final List<FilesPojo> files;

    FilesAdapter(@NonNull List<FilesPojo> list) {
        this.files = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FilesPojo file = files.get(position);
        holder.title.setText(file.getName());
        holder.subTitle.setText(file.getSize());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                if (key.equals(FilesDiffUtilsCallback.KEY_NAME)) {
                    holder.title.setText(o.getString(key));
                } else if (key.equals(FilesDiffUtilsCallback.KEY_SIZE)) {
                    holder.subTitle.setText(o.getString(key));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.files.size();
    }

    void swapList(ArrayList<FilesPojo> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new FilesDiffUtilsCallback(this.files, newList));
        diffResult.dispatchUpdatesTo(this);

        this.files.clear();
        this.files.addAll(newList);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, subTitle;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(android.R.id.text1);
            subTitle = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
}
