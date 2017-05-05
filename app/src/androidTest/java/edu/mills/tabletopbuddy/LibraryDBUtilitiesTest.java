package edu.mills.tabletopbuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.support.test.InstrumentationRegistry;
import android.util.Log;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LibraryDBUtilitiesTest extends AndroidTestCase {
    private SQLiteDatabase db;
    Game SevenWonders;
    Game SettlersofCatan;
    long rowId;
    long rowId2;

    @Before
    public void setUp() throws Exception {
        // This creates a temporary context so database accesses in the tests are isolated.
        RenamingDelegatingContext context =
                new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "_test");
        db = new SQLiteMyLibraryDatabaseHelper(context).getWritableDatabase();

        SevenWonders = new Game("http://cf.geekdo-images.com/images/pic860217.jpg", "7 Wonders",
                "In each age, players receive seven cards from a particular deck, choose one of those " +
                        "cards, then pass the remainder to an adjacent player. ",
                "theme, theme, theme",  "12", 2, 7, 3356, 60);
        SettlersofCatan = new Game("http://cf.geekdo-images.com/images/pic860217.jpg", "Settlers of Catan",
                "description",
                "theme, theme, theme",  "12", 2, 7, 44, 60);


        rowId = LibraryDBUtilities.insertGame(db, SevenWonders);
        rowId2 = LibraryDBUtilities.insertGame(db, SettlersofCatan);
        Log.e("LibraryDBUtilitiesTest", "inserted game into db");
        System.out.println((int) rowId);
    }

    @Test
    public void getGame() throws Exception {
        System.out.println("id num: " + rowId);
        assertEquals(SevenWonders.getBggId(), LibraryDBUtilities.getGame(db, (int) rowId).getBggId());
        //assertEquals(SettlersofCatan, LibraryDBUtilities.getGame(db, (int) rowId2));
    }

    @Test
    public void getGameAfterDelete() throws Exception {
        Integer libraryId = 0;
        //LibraryDBUtilities.removeGame(db, libraryId);
    }

    @After
    public void takeDown() {
        db.close();
    }
}
