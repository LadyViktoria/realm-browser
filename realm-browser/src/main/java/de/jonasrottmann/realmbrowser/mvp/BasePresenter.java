package de.jonasrottmann.realmbrowser.mvp;

import android.support.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface BasePresenter<V extends BaseView.Input> {
    void attachView(V view);

    void detachView();
}
