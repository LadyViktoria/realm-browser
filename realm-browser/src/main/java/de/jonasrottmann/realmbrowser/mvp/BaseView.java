package de.jonasrottmann.realmbrowser.mvp;

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface BaseView {

    interface Input<P extends BasePresenter> {
        void attachPresenter(@Nullable P presenter);
    }

    interface Output {

    }
}
