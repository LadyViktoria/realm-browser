package de.jonasrottmann.realmbrowser.files;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.jonasrottmann.realmbrowser.R;
import de.jonasrottmann.realmbrowser.files.model.FilesPojo;
import java.util.ArrayList;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class FilesActivity extends AppCompatActivity implements FilesContract.View {

    private FilesContract.Presenter presenter;
    private Adapter adapter;

    public static Intent getIntent(@NonNull Context context) {
        Intent intent = new Intent(context, FilesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realm_browser_ac_realm_list_view);
        setSupportActionBar((Toolbar) findViewById(R.id.realm_browser_toolbar));

        // Disable SwipeRefreshLayout - not used in this Activity
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setEnabled(false);

        // Adapter init
        adapter = new Adapter(this, android.R.layout.simple_list_item_2, new ArrayList<FilesPojo>());
        ListView listView = (ListView) findViewById(R.id.realm_browser_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onFileSelected(adapter.getItem(position));
            }
        });

        // Presenter
        attachPresenter((FilesContract.Presenter) getLastCustomNonConfigurationInstance());
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public void attachPresenter(@Nullable FilesContract.Presenter presenter) {
        this.presenter = presenter;
        if (this.presenter == null) {
            this.presenter = new FilesPresenter();
        }
        this.presenter.attachView(this);
    }

    @Override
    public void showFilesList(@NonNull ArrayList<FilesPojo> filesList) {
        adapter.clear();
        adapter.addAll(filesList);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private static class Adapter extends ArrayAdapter<FilesPojo> {

        private final int resource;

        Adapter(Context context, int res, ArrayList<FilesPojo> classes) {
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
}
