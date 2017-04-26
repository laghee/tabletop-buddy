package edu.mills.tabletopbuddy;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
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
import java.util.Arrays;
import java.util.List;

import edu.mills.tabletopbuddy.bggclient.BGG;
import edu.mills.tabletopbuddy.bggclient.common.ThingType;
import edu.mills.tabletopbuddy.bggclient.fetch.FetchException;
import edu.mills.tabletopbuddy.bggclient.fetch.domain.FetchItem;
import edu.mills.tabletopbuddy.bggclient.search.SearchException;
import edu.mills.tabletopbuddy.bggclient.search.domain.SearchItem;
import edu.mills.tabletopbuddy.bggclient.search.domain.SearchOutput;

public class SearchResultsActivity extends ListActivity {

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
                List<SearchItem> results = items.getItems();
                Log.d("Results: ", results.toString());
                return results;
            } catch (SQLiteException e) {
                return null;
            } catch (SearchException e) {
                e.printStackTrace();
                Log.d("Caught:", e.getMessage());
                return null;
            } catch (NullPointerException e) {
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
            } else {
                Toast toast = Toast.makeText(SearchResultsActivity.this, "No results found", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SearchItem item = (SearchItem) getListAdapter().getItem(position);
        int clickedId = item.getId();

        try {
            FetchItem fetchedItem = BGG.fetch(Arrays.asList(clickedId)).iterator().next();
            Intent intent = new Intent(SearchResultsActivity.this, GameDetailActivity.class);
            intent.putExtra(GameDetailActivity.EXTRA_GAMENO, (int) id);
            startActivity(intent);
        } catch (FetchException e) {
            e.printStackTrace();
        }
//        Toast.makeText(this, item + " selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                onSearchRequested();
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
