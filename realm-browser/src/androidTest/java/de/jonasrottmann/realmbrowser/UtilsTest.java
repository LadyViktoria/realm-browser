package de.jonasrottmann.realmbrowser;

import java.lang.reflect.Field;
import java.util.List;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class UtilsTest {
    @Test
    public void testCreateParametrizedNameForListField() throws NoSuchFieldException {
        Field f = TestClass.class.getDeclaredField("stringList");
        assertEquals("List<String>", Utils.createParametrizedName(f));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCreateParametrizedNameForNull() throws NoSuchFieldException {
        //noinspection ConstantConditions
        Utils.createParametrizedName(null);
    }

    private static class TestClass {
        @SuppressWarnings("unused")
        List<String> stringList;
    }

    @Test
    public void createBlobValueStringForNull() throws NoSuchFieldException {
        assertNull(Utils.createBlobValueString(null));
    }

    @Test
    public void createBlobValueStringForEmptyBlob() throws NoSuchFieldException {
        assertEquals("byte[] = {}", Utils.createBlobValueString(new byte[]{}));
    }

    @Test
    public void createBlobValueStringForSingleEntryBlob() throws NoSuchFieldException {
        assertEquals("byte[] = {1}", Utils.createBlobValueString(new byte[]{1}));
    }

    @Test
    public void createBlobValueStringForThreeEntryBlob() throws NoSuchFieldException {
        assertEquals("byte[] = {1, 2, 3}", Utils.createBlobValueString(new byte[]{1,2,3}));
    }
}
