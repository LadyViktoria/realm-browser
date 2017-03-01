package de.jonasrottmann.realmbrowser.browser;

import android.content.Context;
import android.support.annotation.RestrictTo;
import de.jonasrottmann.realmbrowser.basemvp.BasePresenter;
import de.jonasrottmann.realmbrowser.basemvp.BaseView;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface BrowserContract {
    interface Presenter extends BasePresenter<View> {
        void onCreateButton();
    }

    interface View extends BaseView<Presenter> {
        Context getViewContext();
    }
}
