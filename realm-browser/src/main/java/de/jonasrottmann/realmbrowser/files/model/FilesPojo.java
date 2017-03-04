package de.jonasrottmann.realmbrowser.files.model;

import android.support.annotation.RestrictTo;
import lombok.AllArgsConstructor;
import lombok.Value;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@Value
@AllArgsConstructor(suppressConstructorProperties = true)
public class FilesPojo {
    private final String name;
    private final String size;
    private final long sizeInByte;
}
