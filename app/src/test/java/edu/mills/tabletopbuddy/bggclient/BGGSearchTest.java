package edu.mills.tabletopbuddy.bggclient;

import org.junit.Test;

import edu.mills.tabletopbuddy.bggclient.common.ThingType;
import edu.mills.tabletopbuddy.bggclient.search.SearchException;
import edu.mills.tabletopbuddy.bggclient.search.domain.SearchItem;
import edu.mills.tabletopbuddy.bggclient.search.domain.SearchOutput;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class BGGSearchTest {

    @Test
    public void shouldReturnCorrectAmountOfDominionGames() throws SearchException {
        SearchOutput items = BGG.search("dominion", ThingType.BOARDGAME);

        assertThat(items.getTotal() > 50, is(true));
    }

    @Test
    public void shouldFindNoItems() throws SearchException {
        assertThat(BGG.search("a game that should not exist"), is(nullValue()));
    }

    @Test
    public void searchBoardgamesShouldReturnExpansion() throws SearchException {
        int agricolaXDeckId = 38733;

        SearchOutput items = BGG.search("agricola", ThingType.BOARDGAME);

        for (SearchItem item : items.getItems()) {
            if (item.getId() == agricolaXDeckId) {
                return;
            }
        }
        fail("SearchOutput does not contain Agricola X-Deck id");
    }
}
