package de.jonasrottmann.realmbrowser.object.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.view.ViewStub;
import android.widget.TextView;
import de.jonasrottmann.realmbrowser.R;
import de.jonasrottmann.realmbrowser.helper.Utils;
import io.realm.DynamicRealmObject;
import io.realm.RealmObjectSchema;
import java.lang.reflect.Field;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class RealmBrowserViewRealmObject extends RealmBrowserViewField {

    private TextView textView;

    public RealmBrowserViewRealmObject(Context context, @NonNull RealmObjectSchema realmObjectSchema, @NonNull Field field) {
        super(context, realmObjectSchema, field);
        if (!Utils.isRealmObjectField(getField())) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void inflateViewStub() {
        ViewStub stub = (ViewStub) findViewById(R.id.realm_browser_stub);
        stub.setLayoutResource(R.layout.realm_browser_fieldview_textview);
        stub.inflate();
    }

    @Override
    public void initViewStubView() {
        textView = (TextView) findViewById(R.id.realm_browser_field_textview);
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void toggleInputMode(boolean enable) {
        textView.setEnabled(enable);
    }

    @Override
    public boolean isInputValid() {
        return true;
    }

    @Override
    public void setRealmObject(@NonNull DynamicRealmObject realmObject) {
        if (Utils.isRealmObjectField(getField())) {
            if (realmObject.getObject(getField().getName()) == null) {
                getFieldIsNullCheckBox().setChecked(true);
            } else {
                textView.setText(realmObject.getObject(getField().getName()).toString());
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
