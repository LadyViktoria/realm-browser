package de.jonasrottmann.realmbrowser.files;

import android.support.annotation.RestrictTo;
import de.jonasrottmann.realmbrowser.RealmHolder;
import de.jonasrottmann.realmbrowser.RealmModelsActivity;
import de.jonasrottmann.realmbrowser.files.model.FilesDataProvider;
import de.jonasrottmann.realmbrowser.files.model.FilesPojo;
import de.jonasrottmann.realmbrowser.mvp.BasePresenterImpl;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class FilesPresenter extends BasePresenterImpl<FilesContract.View> implements FilesContract.Presenter {

    @Override
    public void attachView(FilesContract.View view) {
        super.attachView(view);
        view.showFilesList(FilesDataProvider.fetchFilesList(getView()));
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
                getView().getViewContext().startActivity(RealmModelsActivity.getIntent(getView().getViewContext()));
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
