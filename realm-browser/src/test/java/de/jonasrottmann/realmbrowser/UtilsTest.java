package de.jonasrottmann.realmbrowser;

import de.jonasrottmann.realmbrowser.helper.Utils;
import java.lang.reflect.Field;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class UtilsTest {
    //region createParametrizedName
    @Test
    public void createParametrizedNameForListField() throws NoSuchFieldException {
        Field f = RealmTestModel.class.getDeclaredField("aStringList");
        assertEquals("List<String>", Utils.createParametrizedName(f));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createParametrizedNameForNull() {
        //noinspection ConstantConditions
        Utils.createParametrizedName(null);
    }
    //endregion

    //region createBlobValueString
    @Test
    public void createBlobValueStringForNull() {
        assertNull(Utils.createBlobValueString(null));
    }

    @Test
    public void createBlobValueStringForEmptyBlob() {
        assertEquals("byte[] = {}", Utils.createBlobValueString(new byte[] {}));
    }

    @Test
    public void createBlobValueStringForSingleEntryBlob() {
        assertEquals("byte[] = {1}", Utils.createBlobValueString(new byte[] { 1 }));
    }

    @Test
    public void createBlobValueStringForThreeEntryBlob() {
        assertEquals("byte[] = {1, 2, 3}", Utils.createBlobValueString(new byte[] { 1, 2, 3 }));
    }
    //endregion

    @Test
    public void isInteger() throws NoSuchFieldException {
        assertTrue(Utils.isInteger(de.jonasrottmann.realmbrowser.RealmTestModel.class.getDeclaredField("anInteger")));
        assertTrue(Utils.isInteger(RealmTestModel.class.getDeclaredField("aBoxedInteger")));
        assertFalse(Utils.isInteger(RealmTestModel.class.getDeclaredField("aLong")));
        assertFalse(Utils.isInteger(RealmTestModel.class.getDeclaredField("aBoxedLong")));
        assertFalse(Utils.isInteger(RealmTestModel.class.getDeclaredField("aShort")));
        assertFalse(Utils.isInteger(RealmTestModel.class.getDeclaredField("aBoxedShort")));
        assertFalse(Utils.isInteger(RealmTestModel.class.getDeclaredField("aByte")));
        assertFalse(Utils.isInteger(RealmTestModel.class.getDeclaredField("aBoxedByte")));
        assertFalse(Utils.isInteger(RealmTestModel.class.getDeclaredField("aDouble")));
        assertFalse(Utils.isInteger(RealmTestModel.class.getDeclaredField("aBoxedDouble")));
        assertFalse(Utils.isInteger(RealmTestModel.class.getDeclaredField("aFloat")));
        assertFalse(Utils.isInteger(RealmTestModel.class.getDeclaredField("aBoxedFloat")));
    }

    @Test
    public void isLong() throws NoSuchFieldException {
        assertFalse(Utils.isLong(RealmTestModel.class.getDeclaredField("anInteger")));
        assertFalse(Utils.isLong(RealmTestModel.class.getDeclaredField("aBoxedInteger")));
        assertTrue(Utils.isLong(RealmTestModel.class.getDeclaredField("aLong")));
        assertTrue(Utils.isLong(RealmTestModel.class.getDeclaredField("aBoxedLong")));
        assertFalse(Utils.isLong(RealmTestModel.class.getDeclaredField("aShort")));
        assertFalse(Utils.isLong(RealmTestModel.class.getDeclaredField("aBoxedShort")));
        assertFalse(Utils.isLong(RealmTestModel.class.getDeclaredField("aByte")));
        assertFalse(Utils.isLong(RealmTestModel.class.getDeclaredField("aBoxedByte")));
        assertFalse(Utils.isLong(RealmTestModel.class.getDeclaredField("aDouble")));
        assertFalse(Utils.isLong(RealmTestModel.class.getDeclaredField("aBoxedDouble")));
        assertFalse(Utils.isLong(RealmTestModel.class.getDeclaredField("aFloat")));
        assertFalse(Utils.isLong(RealmTestModel.class.getDeclaredField("aBoxedFloat")));
    }

    @Test
    public void isShort() throws NoSuchFieldException {
        assertFalse(Utils.isShort(RealmTestModel.class.getDeclaredField("anInteger")));
        assertFalse(Utils.isShort(RealmTestModel.class.getDeclaredField("aBoxedInteger")));
        assertFalse(Utils.isShort(RealmTestModel.class.getDeclaredField("aLong")));
        assertFalse(Utils.isShort(RealmTestModel.class.getDeclaredField("aBoxedLong")));
        assertTrue(Utils.isShort(RealmTestModel.class.getDeclaredField("aShort")));
        assertTrue(Utils.isShort(de.jonasrottmann.realmbrowser.RealmTestModel.class.getDeclaredField("aBoxedShort")));
        assertFalse(Utils.isShort(de.jonasrottmann.realmbrowser.RealmTestModel.class.getDeclaredField("aByte")));
        assertFalse(Utils.isShort(RealmTestModel.class.getDeclaredField("aBoxedByte")));
        assertFalse(Utils.isShort(RealmTestModel.class.getDeclaredField("aDouble")));
        assertFalse(Utils.isShort(RealmTestModel.class.getDeclaredField("aBoxedDouble")));
        assertFalse(Utils.isShort(RealmTestModel.class.getDeclaredField("aFloat")));
        assertFalse(Utils.isShort(RealmTestModel.class.getDeclaredField("aBoxedFloat")));
    }

    @Test
    public void isByte() throws NoSuchFieldException {
        assertFalse(Utils.isByte(RealmTestModel.class.getDeclaredField("anInteger")));
        assertFalse(Utils.isByte(RealmTestModel.class.getDeclaredField("aBoxedInteger")));
        assertFalse(Utils.isByte(RealmTestModel.class.getDeclaredField("aLong")));
        assertFalse(Utils.isByte(RealmTestModel.class.getDeclaredField("aBoxedLong")));
        assertFalse(Utils.isByte(RealmTestModel.class.getDeclaredField("aShort")));
        assertFalse(Utils.isByte(RealmTestModel.class.getDeclaredField("aBoxedShort")));
        assertTrue(Utils.isByte(RealmTestModel.class.getDeclaredField("aByte")));
        assertTrue(Utils.isByte(RealmTestModel.class.getDeclaredField("aBoxedByte")));
        assertFalse(Utils.isByte(RealmTestModel.class.getDeclaredField("aDouble")));
        assertFalse(Utils.isByte(RealmTestModel.class.getDeclaredField("aBoxedDouble")));
        assertFalse(Utils.isByte(RealmTestModel.class.getDeclaredField("aFloat")));
        assertFalse(Utils.isByte(RealmTestModel.class.getDeclaredField("aBoxedFloat")));
    }

    @Test
    public void isFloat() throws NoSuchFieldException {
        assertFalse(Utils.isFloat(RealmTestModel.class.getDeclaredField("anInteger")));
        assertFalse(Utils.isFloat(RealmTestModel.class.getDeclaredField("aBoxedInteger")));
        assertFalse(Utils.isFloat(RealmTestModel.class.getDeclaredField("aLong")));
        assertFalse(Utils.isFloat(RealmTestModel.class.getDeclaredField("aBoxedLong")));
        assertFalse(Utils.isFloat(RealmTestModel.class.getDeclaredField("aShort")));
        assertFalse(Utils.isFloat(RealmTestModel.class.getDeclaredField("aBoxedShort")));
        assertFalse(Utils.isFloat(RealmTestModel.class.getDeclaredField("aByte")));
        assertFalse(Utils.isFloat(RealmTestModel.class.getDeclaredField("aBoxedByte")));
        assertFalse(Utils.isFloat(RealmTestModel.class.getDeclaredField("aDouble")));
        assertFalse(Utils.isFloat(RealmTestModel.class.getDeclaredField("aBoxedDouble")));
        assertTrue(Utils.isFloat(RealmTestModel.class.getDeclaredField("aFloat")));
        assertTrue(Utils.isFloat(RealmTestModel.class.getDeclaredField("aBoxedFloat")));
    }

    @Test
    public void isDouble() throws NoSuchFieldException {
        assertFalse(Utils.isDouble(RealmTestModel.class.getDeclaredField("anInteger")));
        assertFalse(Utils.isDouble(RealmTestModel.class.getDeclaredField("aBoxedInteger")));
        assertFalse(Utils.isDouble(RealmTestModel.class.getDeclaredField("aLong")));
        assertFalse(Utils.isDouble(RealmTestModel.class.getDeclaredField("aBoxedLong")));
        assertFalse(Utils.isDouble(RealmTestModel.class.getDeclaredField("aShort")));
        assertFalse(Utils.isDouble(RealmTestModel.class.getDeclaredField("aBoxedShort")));
        assertFalse(Utils.isDouble(RealmTestModel.class.getDeclaredField("aByte")));
        assertFalse(Utils.isDouble(RealmTestModel.class.getDeclaredField("aBoxedByte")));
        assertTrue(Utils.isDouble(RealmTestModel.class.getDeclaredField("aDouble")));
        assertTrue(Utils.isDouble(RealmTestModel.class.getDeclaredField("aBoxedDouble")));
        assertFalse(Utils.isDouble(RealmTestModel.class.getDeclaredField("aFloat")));
        assertFalse(Utils.isDouble(RealmTestModel.class.getDeclaredField("aBoxedFloat")));
    }

    @Test
    public void isBoolean() throws NoSuchFieldException {
        assertTrue(Utils.isBoolean(RealmTestModel.class.getDeclaredField("aBool")));
        assertTrue(Utils.isBoolean(RealmTestModel.class.getDeclaredField("aBoxedBool")));
    }

    @Test
    public void isString() throws NoSuchFieldException {
        assertTrue(Utils.isString(RealmTestModel.class.getDeclaredField("aString")));
    }

    @Test
    public void isBlob() throws NoSuchFieldException {
        assertTrue(Utils.isBlob(RealmTestModel.class.getDeclaredField("aBlob")));
    }

    @Test
    public void isDate() throws NoSuchFieldException {
        assertTrue(Utils.isDate(RealmTestModel.class.getDeclaredField("aDate")));
    }

    @Test
    public void isParametrizedField() throws NoSuchFieldException {
        assertTrue(Utils.isParametrizedField(RealmTestModel.class.getDeclaredField("aRealmList")));
        assertTrue(Utils.isParametrizedField(RealmTestModel.class.getDeclaredField("aStringList")));
    }

    @Test
    public void isRealmObjectField() throws NoSuchFieldException {
        assertTrue(Utils.isRealmObjectField(RealmTestModel.class.getDeclaredField("anObject")));
        assertFalse(Utils.isRealmObjectField(RealmTestModel.class.getDeclaredField("aRealmList")));
    }
}
