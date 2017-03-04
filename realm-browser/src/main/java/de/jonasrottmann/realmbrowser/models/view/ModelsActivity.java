package de.jonasrottmann.realmbrowser.models.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import de.jonasrottmann.realmbrowser.R;
import de.jonasrottmann.realmbrowser.models.ModelsContract;
import de.jonasrottmann.realmbrowser.models.ModelsPresenter;
import de.jonasrottmann.realmbrowser.models.model.ModelPojo;
import java.util.ArrayList;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ModelsActivity extends AppCompatActivity implements ModelsContract.View, SearchView.OnQueryTextListener {
    private ModelsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ModelsContract.Presenter presenter;
    private MenuItem sortMenuItem;

    private static final String SEARCH_KEY = "search";
    private SearchView searchView;
    private String searchString;

    public static Intent getIntent(@NonNull Context context) {
        Intent intent = new Intent(context, ModelsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realm_browser_ac_realm_list_view);
        setSupportActionBar((Toolbar) findViewById(R.id.realm_browser_toolbar));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestForContentUpdate();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.realm_browser_dark_purple);

        adapter = new ModelsAdapter(this, android.R.layout.simple_list_item_2, new ArrayList<ModelPojo>());
        ListView listView = (ListView) findViewById(R.id.realm_browser_recycler);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClicked(adapter.getItem(position));
            }
        });

        if (savedInstanceState != null) {
            searchString = savedInstanceState.getString(SEARCH_KEY);
        }

        // Presenter
        attachPresenter((ModelsContract.Presenter) getLastCustomNonConfigurationInstance());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_KEY, searchString);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.realm_browser_menu_modelsactivity, menu);

        this.sortMenuItem = menu.findItem(R.id.realm_browser_action_sort);

        MenuItem searchMenuItem = menu.findItem(R.id.realm_browser_action_filter);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (searchString != null && !searchString.isEmpty()) {
            ((SearchView) menu.findItem(R.id.realm_browser_action_filter).getActionView()).setIconified(false);
            searchView.requestFocus();
            searchView.setQuery(searchString, true);
            searchView.clearFocus();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.realm_browser_action_sort) {
            presenter.onSortModeChanged();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void onItemClicked(ModelPojo pojo) {
        presenter.onModelSelected(pojo);
    }

    @Override
    public void updateView(@NonNull ArrayList<ModelPojo> filesList, @ModelsContract.SortMode int sortMode) {
        adapter.clear();
        adapter.addAll(filesList);
        adapter.notifyDataSetChanged();
        if (sortMode == ModelsContract.SortMode.ASC && sortMenuItem != null) {
            this.sortMenuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.realm_browser_ic_sort_ascending_white_24dp));
        } else if (sortMode == ModelsContract.SortMode.DESC && sortMenuItem != null) {
            this.sortMenuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.realm_browser_ic_sort_descending_white_24dp));
        }
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void attachPresenter(@Nullable ModelsContract.Presenter presenter) {
        this.presenter = presenter;
        if (this.presenter == null) {
            this.presenter = new ModelsPresenter();
        }
        this.presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.searchString = newText;
        presenter.onFilterChanged(newText);
        return true;
    }
}
