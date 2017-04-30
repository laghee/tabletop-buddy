package edu.mills.tabletopbuddy;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

// Correctly queries games added to MyGameDatabase and populates view
// of personal library.
// Users are able to add and delete games from their local library.
public class MyLibraryActivity extends ListActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private Cursor altCursor;
    private CursorAdapter gameCursorAdapter;

    //view saved games
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
//            Integer gameDeleted = (Integer) getIntent().getExtras().get(GameDetailActivity.EXTRA_GAMENO);
//            gameCursorAdapter.changeCursor(altCursor);
            gameCursorAdapter.notifyDataSetChanged();
        } else {
            new GameLibraryTask().execute();
            //setContentView(R.layout.activity_my_library);
        }
    }

    private class GameLibraryTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... games) {
            SQLiteOpenHelper gameLibraryHelper = new SQLiteMyLibraryDatabaseHelper(MyLibraryActivity.this);
            db = gameLibraryHelper.getReadableDatabase();

            try {
                cursor = db.query("LIBRARY",
                        new String[]{"_id", "NAME"},
                        null, null, null, null, null);
            } catch (SQLiteException e) {
                return null;
            }
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);

        ListView listGames = getListView();
        if (cursor != null) {
            gameCursorAdapter = new SimpleCursorAdapter(MyLibraryActivity.this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listGames.setAdapter(gameCursorAdapter);
        } else {
            Toast toast = Toast.makeText(MyLibraryActivity.this, "Database error", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}


    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onListItemClick(ListView listView,
                                View itemView,
                                int position,
                                long id) {
        Intent intent = new Intent(MyLibraryActivity.this, GameDetailActivity.class);
        intent.putExtra(GameDetailActivity.EXTRA_GAMENO, (int) id);
        intent.putExtra(GameDetailActivity.EXTRA_CLASSNAME, "MyLibraryActivity");
        startActivity(intent);
    }


}