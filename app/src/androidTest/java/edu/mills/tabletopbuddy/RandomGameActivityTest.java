package edu.mills.tabletopbuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)

public class RandomGameActivityTest extends AndroidTestCase {
    private SQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        // This creates a temporary context so database accesses in the tests are isolated.
        RenamingDelegatingContext context =
                new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "_test");
        db = new SQLiteMyLibraryDatabaseHelper(context).getWritableDatabase();
    }


    @Test
    public void shouldReturnNull() throws Exception {
        RandomGameActivity testGame = new RandomGameActivity();
        assertNull(null, testGame);
    }


    @After
    public void takeDown() {
        db.close();
    }


}
