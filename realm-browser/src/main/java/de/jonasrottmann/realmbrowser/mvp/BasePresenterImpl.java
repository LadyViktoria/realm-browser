package de.jonasrottmann.realmbrowser.mvp;

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.UiThread;
import java.lang.ref.WeakReference;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public abstract class BasePresenterImpl<V extends BaseView.Input> implements BasePresenter<V> {

    private WeakReference<V> viewRef;

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
    }

    @UiThread
    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    @UiThread
    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @UiThread
    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
}
