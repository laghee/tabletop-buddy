package edu.mills.tabletopbuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.support.test.InstrumentationRegistry;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LibraryDBUtilitiesTest extends AndroidTestCase {
    private SQLiteDatabase db;
    Game SevenWonders;

    @Before
    public void setUp() throws Exception {
        // This creates a temporary context so database accesses in the tests are isolated.
        RenamingDelegatingContext context =
                new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "_test");
        db = new SQLiteMyLibraryDatabaseHelper(context).getWritableDatabase();
        SevenWonders = new Game("http://cf.geekdo-images.com/images/pic860217.jpg", "7 Wonders",
                "In each age, players receive seven cards from a particular deck, choose one of those " +
                        "cards, then pass the remainder to an adjacent player. ",
                        "theme, theme, theme", "12", 2, 7, 30, 3356);

        LibraryDBUtilities.insertGame(db, SevenWonders);
    }

    @Test
    public void getGame() throws Exception {
        int gameId = 0;
        assertEquals(SevenWonders, LibraryDBUtilities.getGame(db, gameId));
    }

    @Test
    public void deleteGame() throws Exception {
    }

    @Test
    public void getGameAfterDelete() throws Exception {
    }

    @After
    public void takeDown() {
        db.close();
    }
}
