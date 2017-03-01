package de.jonasrottmann.realmbrowser.models.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.jonasrottmann.realmbrowser.models.model.ModelPojo;
import java.util.ArrayList;
import java.util.Locale;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class ModelsAdapter extends ArrayAdapter<ModelPojo> {

    private final int res;

    ModelsAdapter(Context context, int res, ArrayList<ModelPojo> modelPojos) {
        super(context, res, modelPojos);
        this.res = res;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ModelPojo modelPojo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(res, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(android.R.id.text1);
        TextView count = (TextView) convertView.findViewById(android.R.id.text2);

        title.setText(modelPojo.getKlass().getSimpleName());
        count.setText(String.format(Locale.US, "%d %s", modelPojo.getCount(), "rows"));

        return convertView;
    }
}