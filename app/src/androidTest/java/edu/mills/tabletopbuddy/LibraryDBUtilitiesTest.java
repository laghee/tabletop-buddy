package edu.mills.tabletopbuddy;

import org.junit.Test;

import java.util.Arrays;

import edu.mills.tabletopbuddy.bggclient.BGG;
import edu.mills.tabletopbuddy.bggclient.common.ThingType;
import edu.mills.tabletopbuddy.bggclient.fetch.FetchException;
import edu.mills.tabletopbuddy.bggclient.fetch.domain.FetchItem;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Created by kristencutler on 5/3/2017.
 */

public class LibraryDBUtilitiesTest {

    @Test
    public void shouldFetchDieMacher() throws FetchException {
        int dieMacherId = 1;

        FetchItem item = BGG.fetch(Arrays.asList(dieMacherId), ThingType.BOARDGAME).iterator().next();

        assertThat(item.getName(), containsString("Macher"));
    }

    @Test
    public void shouldFetchAgricolaXDeck() throws FetchException {
        int agricolaXDeckId = 38733;

        FetchItem item = BGG.fetch(Arrays.asList(agricolaXDeckId), ThingType.BOARDGAME_EXPANSION).iterator().next();

        assertThat(item.getName(), containsString("Agricola"));
    }
}
