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

public class LibraryDBUtilitiesTest extends AndroidTestCase {
    private SQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        // This creates a temporary context so database accesses in the tests are isolated.
        RenamingDelegatingContext context =
                new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "_test");
        db = new SQLiteMyLibraryDatabaseHelper(context).getWritableDatabase();

        // Add Games to DB
        long rowId = GoDatabaseUtilities.addMeeting(db, PROF1_ID, MEETING1_START);
        GoDatabaseUtilities.endMeeting(db, rowId, MEETING1_TEXT, MEETING1_END);
        rowId = GoDatabaseUtilities.addMeeting(db, PROF1_ID, MEETING2_START);
        GoDatabaseUtilities.endMeeting(db, rowId, MEETING2_TEXT, MEETING2_END);

        // Create one meeting with PROF2.
        rowId = GoDatabaseUtilities.addMeeting(db, PROF2_ID);
        GoDatabaseUtilities.endMeeting(db, rowId, null);
    }

    @After
    public void takeDown() {
        db.close();
    }


    @Test
    public void getGame() throws Exception {
        assertEquals(game, LibraryDBUtilities.get());
    }

    @Test
    public void deleteGame() throws Exception {
        assertEquals(??, LibraryDBUtilities.delete());
    }

    @Test
    public void getGameAfterDelete() throws Exception {
        assertEquals(game, LibraryDBUtilities.get());
    }
}
