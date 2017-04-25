package edu.mills.tabletopbuddy;


import org.junit.Test;

import java.util.Arrays;

import edu.mills.tabletopbuddy.bggclient.BGG;
import edu.mills.tabletopbuddy.bggclient.common.ThingType;
import edu.mills.tabletopbuddy.bggclient.fetch.FetchException;
import edu.mills.tabletopbuddy.bggclient.fetch.domain.FetchItem;
import edu.mills.tabletopbuddy.bggclient.search.SearchException;
import edu.mills.tabletopbuddy.bggclient.search.domain.SearchItem;
import edu.mills.tabletopbuddy.bggclient.search.domain.SearchOutput;

public class BGG_SearchTest {

    @Test
    public void shouldReturnCatanlist() throws SearchException {
        SearchOutput items = BGG.search("arkham", ThingType.BOARDGAME);

        for (SearchItem item : items.getItems()) {
            System.out.println(item.getName().getValue());
        }
    }


    @Test
    public void shouldReturnItemDetails() throws FetchException {
        int catanjuniorID = 184842;

        FetchItem catanjunior = BGG.fetch(Arrays.asList(catanjuniorID)).iterator().next();

        System.out.println(catanjunior.getId());

        System.out.println(catanjunior.getName());

        System.out.println(catanjunior.getThumbnailUrl());

        System.out.println(catanjunior.getDescription());

        System.out.println(catanjunior.getCategories());

        System.out.println("Players: " + catanjunior.getMinPlayers().getValue() + " - " + catanjunior.getMaxPlayers().getValue());

        System.out.println("Min age: " +catanjunior.getMinAge().getValue());

        System.out.println("Playing time: " + catanjunior.getPlayingTime().getValue());

    }
}
