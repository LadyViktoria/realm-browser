package de.jonasrottmann.realmbrowser.files.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.jonasrottmann.realmbrowser.files.model.FilesPojo;
import java.util.ArrayList;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class FilesAdapter extends ArrayAdapter<FilesPojo> {

    private final int resource;

    FilesAdapter(Context context, int res, ArrayList<FilesPojo> classes) {
        super(context, res, classes);
        resource = res;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        FilesPojo file = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(android.R.id.text1);
        TextView filesize = (TextView) convertView.findViewById(android.R.id.text2);

        title.setText(file.getName());
        filesize.setText(file.getSize());

        return convertView;
    }
}
