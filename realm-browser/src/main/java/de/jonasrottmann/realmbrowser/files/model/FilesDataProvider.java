package de.jonasrottmann.realmbrowser.files.model;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.text.format.Formatter;
import de.jonasrottmann.realmbrowser.files.FilesContract;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public final class FilesDataProvider {

    private FilesDataProvider() {
    }

    private static List<String> ignoreExtensionList = new ArrayList<>();

    static {
        ignoreExtensionList.add(".log");
        ignoreExtensionList.add(".log_a");
        ignoreExtensionList.add(".log_b");
        ignoreExtensionList.add(".lock");
        ignoreExtensionList.add(".management");
        ignoreExtensionList.add(".temp");
    }

    @NonNull
    public static ArrayList<FilesPojo> fetchFilesList(FilesContract.View view) {
        File dataDir = new File(view.getViewContext().getApplicationInfo().dataDir, "files");
        File[] files = dataDir.listFiles();
        ArrayList<FilesPojo> fileList = new ArrayList<>();
        for (File file : files) {
            String fileName = file.getName();
            if (isValidFileName(fileName)) {
                fileList.add(new FilesPojo(fileName, Formatter.formatShortFileSize(view.getViewContext(), file.length())));
            }
        }
        return fileList;
    }

    private static boolean isValidFileName(String fileName) {
        return fileName.lastIndexOf(".") > 0 && !ignoreExtensionList.contains(fileName.substring(fileName.lastIndexOf(".")));
    }
}
