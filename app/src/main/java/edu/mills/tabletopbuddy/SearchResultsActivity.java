/**
 * Implementation of the Tabletop Buddy application. Created for Mills
 * CS 115: Mobile Application Development, Spring 2017.
 *
 * @author Kristen Cutler, Jennifer Diaz, Arianne Agogino Gieringer,
 * Kate Manning, Erin Walter
 */
package edu.mills.tabletopbuddy;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.mills.tabletopbuddy.bggclient.BGG;
import edu.mills.tabletopbuddy.bggclient.common.ThingType;
import edu.mills.tabletopbuddy.bggclient.search.SearchException;
import edu.mills.tabletopbuddy.bggclient.search.domain.SearchItem;
import edu.mills.tabletopbuddy.bggclient.search.domain.SearchOutput;

/**
 * Performs a query to the BGG server database by name using BGG client API.
 * Returns search results in a list, enabling a user to view the game's details
 * once {@link GameDetailActivity} is launched.
 */
public class SearchResultsActivity extends ListActivity {
    private List<SearchItem> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            handleIntent(getIntent());
        } catch (SearchException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onNewIntent(Intent intent) {
        try {
            handleIntent(intent);
        } catch (SearchException e) {
            e.printStackTrace();
        }
    }

    private void handleIntent(Intent intent) throws SearchException {

        // handles a click on a search suggestion; launches activity to show word
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Intent wordIntent = new Intent(this, GameDetailActivity.class);
            wordIntent.setData(intent.getData());
            startActivity(wordIntent);
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            new SearchBGGTask().execute(query);
        }
    }

    private class SearchBGGTask extends AsyncTask<String, Void, List<SearchItem>>  {
        @Override
        protected List<SearchItem> doInBackground(String... params) {
            String query = params[0];
            try {
                SearchOutput items = BGG.search(query, ThingType.BOARDGAME);
                if (items != null) {
                    results = items.getItems();
                    Log.d("Results: ", results.toString());
                    return results;
                } else {
                    return null;
                }
            } catch (SearchException e) {
                e.printStackTrace();
                Log.d("Caught:", e.getMessage());
                return null;
            }
        }


        @Override
        protected void onPostExecute(List<SearchItem> res) {
            if (res != null) {
                List<String> names = new ArrayList<>();
                for (SearchItem item : res) {
                    names.add(item.getName().getValue());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchResultsActivity.this,
                        android.R.layout.simple_list_item_1, names);
                SearchResultsActivity.this.setListAdapter(adapter);

                List<Integer> resultIds = new ArrayList<>();
                for (SearchItem item : res) {
                    resultIds.add(item.getId());
                }
            } else {
                Toast toast = Toast.makeText(SearchResultsActivity.this, "No results found", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
//        String item = (String) getListAdapter().getItem(position);
//        Toast.makeText(this, item + " selected", Toast.LENGTH_SHORT).show();

        SearchItem searchItem = results.get(position);
        int clickedId = searchItem.getId();

        Intent intent = new Intent(SearchResultsActivity.this, GameDetailActivity.class);
        intent.putExtra(GameDetailActivity.EXTRA_GAMENO, clickedId);
        intent.putExtra(GameDetailActivity.EXTRA_CLASSNAME, "SearchResultsActivity");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                onSearchRequested();
                return true;
            case R.id.to_search:
                startActivity(new Intent(this, SearchResultsActivity.class));
                return true;
            case R.id.main:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.random:
                startActivity(new Intent(this, RandomGameActivity.class));
                return true;
            case R.id.library:
                startActivity(new Intent(this, MyLibraryActivity.class));
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
        return true;
    }

}
