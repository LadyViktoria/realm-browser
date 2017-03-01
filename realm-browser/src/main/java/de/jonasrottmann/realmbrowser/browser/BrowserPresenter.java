package de.jonasrottmann.realmbrowser.browser;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import de.jonasrottmann.realmbrowser.basemvp.BasePresenterImpl;
import de.jonasrottmann.realmbrowser.helper.RealmHolder;
import io.realm.DynamicRealm;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class BrowserPresenter extends BasePresenterImpl<BrowserContract.View> implements BrowserContract.Presenter {

    private DynamicRealm mDynamicRealm;

    @Override
    public void attachView(@NonNull BrowserContract.View view) {
        super.attachView(view);
        mDynamicRealm = DynamicRealm.getInstance(RealmHolder.getInstance().getRealmConfiguration());
        // TODO add change listener

        // view.showRealmContents();
    }

    @Override
    public void detachView() {
        if (mDynamicRealm != null && !mDynamicRealm.isClosed()) {
            mDynamicRealm.close();
        }
        super.detachView();
    }


    @Override
    public void onCreateButton() {

    }
}
