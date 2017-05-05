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
    Game IAmABanana;
    long rowId;
    long rowId2;
    long rowId3;

    @Before
    public void setUp() throws Exception {
        // This creates a temporary context so database accesses in the tests are isolated.
        RenamingDelegatingContext context =
                new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "_test");
        db = new SQLiteMyLibraryDatabaseHelper(context).getWritableDatabase();

        SevenWonders = new Game("http://cf.geekdo-images.com/images/pic860217.jpg", "7 Wonders",
                "In each age, players receive seven cards from a particular deck, choose one of those " +
                        "cards, then pass the remainder to an adjacent player. ",
                "theme, theme, theme",  3356, 2, 7, 60, "12");
        SettlersofCatan = new Game("http://cf.geekdo-images.com/images/pic860217.jpg", "Settlers of Catan",
                "description", "theme, theme, theme", 4444, 7, 44, 60, "12");
        IAmABanana = new Game("http://cf.geekdo-images.com/images/pic860217.jpg", "Settlers of Catan",
                "description", "theme, theme, theme", 1234, 10, 44, 90, "12");

        //Create 3 games
        rowId = LibraryDBUtilities.insertGame(db, SevenWonders);
        rowId2 = LibraryDBUtilities.insertGame(db, SettlersofCatan);
        rowId3 = LibraryDBUtilities.insertGame(db, IAmABanana);
    }

    @Test
    public void getGame() throws Exception {
        //Verify games match the games inserted into db
        assertEquals(SevenWonders.getBggId(), LibraryDBUtilities.getGame(db, (int) rowId).getBggId());
        assertEquals(SettlersofCatan.getBggId(), LibraryDBUtilities.getGame(db, (int) rowId2).getBggId());
        assertEquals(IAmABanana.getBggId(), LibraryDBUtilities.getGame(db, (int) rowId3).getBggId());
    }

    @Test
    public void getGameAfterDelete() throws Exception {
        //remove 2 games from the db
        LibraryDBUtilities.removeGameByBGGId(db, SevenWonders.getBggId());
        LibraryDBUtilities.removeGameByBGGId(db, SettlersofCatan.getBggId());
        assertNull(null, LibraryDBUtilities.getGame(db, SevenWonders.getBggId()));
        assertNull(null, LibraryDBUtilities.getGame(db, SettlersofCatan.getBggId()));

    }

    @After
    public void takeDown() {
        db.close();
    }
}
