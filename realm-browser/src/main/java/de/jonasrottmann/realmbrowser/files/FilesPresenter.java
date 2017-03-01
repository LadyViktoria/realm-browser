package de.jonasrottmann.realmbrowser.files;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import de.jonasrottmann.realmbrowser.basemvp.BasePresenterImpl;
import de.jonasrottmann.realmbrowser.files.model.FilesPojo;
import de.jonasrottmann.realmbrowser.files.model.FilesUsecase;
import de.jonasrottmann.realmbrowser.helper.RealmHolder;
import de.jonasrottmann.realmbrowser.models.view.ModelsActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class FilesPresenter extends BasePresenterImpl<FilesContract.View> implements FilesContract.Presenter {

    @Override
    public void attachView(@NonNull FilesContract.View view) {
        super.attachView(view);
        view.showFilesList(FilesUsecase.fetchFilesList(getView()));
    }

    @Override
    public void onFileSelected(FilesPojo item) {
        try {
            RealmConfiguration config = new RealmConfiguration.Builder().name(item.getName()).build();
            RealmHolder.getInstance().setRealmConfiguration(config);
            Realm realm = Realm.getInstance(config);
            realm.close();
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().getViewContext().startActivity(ModelsActivity.getIntent(getView().getViewContext()));
            }
        } catch (RealmMigrationNeededException e) {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showToast("RealmMigrationNeededException");
            }
        } catch (Exception e) {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showToast("Can't open realm instance");
            }
        }
    }
}
