package de.jonasrottmann.realmbrowser.files.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import de.jonasrottmann.realmbrowser.R;
import de.jonasrottmann.realmbrowser.files.FilesContract;
import de.jonasrottmann.realmbrowser.files.FilesPresenter;
import de.jonasrottmann.realmbrowser.files.model.FilesPojo;
import java.util.ArrayList;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class FilesActivity extends AppCompatActivity implements FilesContract.View {

    private FilesContract.Presenter presenter;
    private FilesAdapter adapter;

    public static Intent getIntent(@NonNull Context context) {
        Intent intent = new Intent(context, FilesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realm_browser_ac_files);
        setSupportActionBar((Toolbar) findViewById(R.id.realm_browser_toolbar));

        // Disable SwipeRefreshLayout - not used in this Activity
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setEnabled(false);

        // Adapter init
        adapter = new FilesAdapter(null);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.realm_browser_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
        //    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //        presenter.onFileSelected(adapter.getItem(position));
        //    }
        //});

        // Presenter
        attachPresenter((FilesContract.Presenter) getLastCustomNonConfigurationInstance());
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
        adapter.swapList(filesList);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
