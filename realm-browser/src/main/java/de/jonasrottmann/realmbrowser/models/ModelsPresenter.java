package de.jonasrottmann.realmbrowser.models;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import de.jonasrottmann.realmbrowser.basemvp.BasePresenterImpl;
import de.jonasrottmann.realmbrowser.browser.view.RealmBrowserActivity;
import de.jonasrottmann.realmbrowser.models.model.ModelPojo;
import java.util.ArrayList;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ModelsPresenter extends BasePresenterImpl<ModelsContract.View> implements ModelsContract.Presenter {
    @ModelsContract.SortMode
    private int sortMode = ModelsContract.SortMode.ASC;
    private String filter;
    private final ModelsInteractor interactor;

    public ModelsPresenter() {
        interactor = new ModelsInteractor(this);
    }

    //region ViewInput
    @Override
    public void attachView(@NonNull ModelsContract.View view) {
        super.attachView(view);
    }

    @Override
    public void onSortModeChanged() {
        //noinspection WrongConstant
        sortMode = (sortMode + 1) % 2;
        interactor.getModelPojos(this.sortMode, this.filter);
    }

    @Override
    public void requestForContentUpdate() {
        interactor.getModelPojos(this.sortMode, this.filter);
    }

    @Override
    public void onFilterChanged(@NonNull String filter) {
        this.filter = filter;
        interactor.getModelPojos(this.sortMode, this.filter);
    }

    @Override
    public void onModelSelected(ModelPojo item) {
        if (isViewAttached()) {
            //noinspection ConstantConditions
            RealmBrowserActivity.start(getView().getViewContext(), item.getKlass());
        }
    }
    //endregion

    //region InteractorInput
    @Override
    public void updateWithModels(@NonNull ArrayList<ModelPojo> modelsList, @ModelsContract.SortMode int sortMode) {
        if (isViewAttached()) {
            //noinspection ConstantConditions
            getView().updateWithModels(modelsList, sortMode);
        }
    }
    //endregion
}
