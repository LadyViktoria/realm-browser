package de.jonasrottmann.realmbrowser.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import de.jonasrottmann.realmbrowser.basemvp.BasePresenterImpl;
import de.jonasrottmann.realmbrowser.browser.view.RealmBrowserActivity;
import de.jonasrottmann.realmbrowser.helper.RealmHolder;
import de.jonasrottmann.realmbrowser.models.model.ModelPojo;
import io.realm.Realm;
import io.realm.RealmModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ModelsPresenter extends BasePresenterImpl<ModelsContract.View> implements ModelsContract.Presenter {
    @ModelsContract.SortMode
    private int sortMode = ModelsContract.SortMode.ASC;
    private ArrayList<ModelPojo> pojos;
    private String filter;

    @Override
    public void onSortModeChanged() {
        if (this.pojos == null) {
            this.pojos = getModelPojos();
        }
        //noinspection WrongConstant
        sortMode = (sortMode + 1) % 2;
        if (isViewAttached()) {
            //noinspection ConstantConditions
            getView().updateView(filterPojos(sortedModelPojos(pojos, this.sortMode), filter), sortMode);
        }
    }

    @Override
    public void requestForContentUpdate() {
        this.pojos = getModelPojos();
        if (isViewAttached()) {
            //noinspection ConstantConditions
            getView().updateView(filterPojos(sortedModelPojos(pojos, this.sortMode), filter), sortMode);
        }
    }

    @Override
    public void onFilterChanged(@NonNull String filter) {
        this.filter = filter;
        ArrayList<ModelPojo> filteredPojos = filterPojos(pojos, filter);
        if (isViewAttached()) {
            //noinspection ConstantConditions
            getView().updateView(filteredPojos == null ? pojos : filteredPojos, sortMode);
        }
    }

    @Override
    public void onModelSelected(ModelPojo item) {
        if (isViewAttached()) {
            //noinspection ConstantConditions
            RealmBrowserActivity.start(getView().getViewContext(), item.getKlass());
        }
    }

    @Override
    public void attachView(@NonNull ModelsContract.View view) {
        super.attachView(view);
        if (this.pojos == null) {
            this.pojos = getModelPojos();
        }
        view.updateView(filterPojos(sortedModelPojos(pojos, this.sortMode), filter), this.sortMode);
    }

    //region HELPER
    @NonNull
    private static ArrayList<ModelPojo> getModelPojos() {
        Realm realm = Realm.getInstance(RealmHolder.getInstance().getRealmConfiguration());
        ArrayList<Class<? extends RealmModel>> realmModelClasses = new ArrayList<>(realm.getConfiguration().getRealmObjectClasses());
        ArrayList<ModelPojo> pojos = new ArrayList<>();
        for (Class<? extends RealmModel> klass : realmModelClasses) {
            pojos.add(new ModelPojo(klass, realm.where(klass).findAll().size()));
        }
        realm.close();
        return pojos;
    }

    @NonNull
    private static ArrayList<ModelPojo> sortedModelPojos(@NonNull ArrayList<ModelPojo> pojos, @ModelsContract.SortMode int sortMode) {
        Collections.sort(pojos, new Comparator<ModelPojo>() {
            @Override
            public int compare(ModelPojo o1, ModelPojo o2) {
                return o1.getKlass().getSimpleName().compareTo(o2.getKlass().getSimpleName());
            }
        });
        if (sortMode == ModelsContract.SortMode.ASC) {
            Collections.reverse(pojos);
        }
        return pojos;
    }

    @NonNull
    private static ArrayList<ModelPojo> filterPojos(@NonNull ArrayList<ModelPojo> pojos, @Nullable String filter) {
        ArrayList<ModelPojo> filteredPojos = null;
        if (filter != null && !filter.isEmpty()) {
            filteredPojos = new ArrayList<>();
            for (ModelPojo pojo : pojos) {
                if (pojo.getKlass().getSimpleName().toLowerCase().contains(filter.toLowerCase())) {
                    filteredPojos.add(pojo);
                }
            }
        }
        return filteredPojos == null ? pojos : filteredPojos;
    }
    //endregion
}
