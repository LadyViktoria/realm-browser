package de.jonasrottmann.realmbrowser.files.model;

import android.support.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class FilesPojo {
    private final String name;
    private final String size;

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public FilesPojo(String name, String size) {
        this.name = name;
        this.size = size;
    }
}
