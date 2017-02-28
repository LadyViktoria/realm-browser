package de.jonasrottmann.realmbrowser.files;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.util.Pair;
import de.jonasrottmann.realmbrowser.files.model.FilesPojo;
import de.jonasrottmann.realmbrowser.basemvp.BasePresenter;
import de.jonasrottmann.realmbrowser.basemvp.BaseView;
import java.util.ArrayList;
import java.util.List;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface FilesContract {

    interface Presenter extends BasePresenter<View> {
        void onFileSelected(FilesPojo item);
    }

    interface View extends BaseView.Input<Presenter> {
        /**
         * @param filesList A {@link List} containing information about the files to display. Elements are {@link Pair}s, where first value of the tupel is the file name and the second the filesize
         * (already formatted).
         */
        void showFilesList(@NonNull ArrayList<FilesPojo> filesList);

        Context getViewContext();

        void showToast(String message);
    }
}
