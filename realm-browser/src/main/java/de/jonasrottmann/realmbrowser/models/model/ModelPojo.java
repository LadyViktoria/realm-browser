package de.jonasrottmann.realmbrowser.models.model;

import android.support.annotation.RestrictTo;
import io.realm.RealmModel;
import lombok.AllArgsConstructor;
import lombok.Value;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@Value
@AllArgsConstructor(suppressConstructorProperties = true)
public class ModelPojo {
    final Class<? extends RealmModel> klass;
    long count;
}
