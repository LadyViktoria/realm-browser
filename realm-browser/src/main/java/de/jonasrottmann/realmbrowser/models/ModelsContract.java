package de.jonasrottmann.realmbrowser.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import de.jonasrottmann.realmbrowser.models.model.ModelsPojo;
import de.jonasrottmann.realmbrowser.basemvp.BasePresenter;
import de.jonasrottmann.realmbrowser.basemvp.BaseView;
import java.util.ArrayList;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface ModelsContract {
    interface Presenter extends BasePresenter<ModelsContract.View> {
        void onModelSelected(ModelsPojo item);
    }

    interface View extends BaseView.Input<ModelsContract.Presenter> {
        void showModelsList(@NonNull ArrayList<ModelsPojo> filesList);

        Context getViewContext();

        void showToast(String message);
    }
}