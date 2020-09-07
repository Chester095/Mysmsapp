package com.example.budgetapp.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.budgetapp.Data.BudgetContract.ArticleEntry;


public class ArticleContentProvider extends ContentProvider {

    ArticleDBHelper dpOpenHelper;
    private static final int MEMBERS = 111, MEMBER_ID = 222;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(BudgetContract.AUTHORITY, BudgetContract.PATH_MEMBERS_ARTICLE, MEMBERS);
        uriMatcher.addURI(BudgetContract.AUTHORITY, BudgetContract.PATH_MEMBERS_ARTICLE + "/#", MEMBER_ID);
    }

    @Override
    public boolean onCreate() {
        Log.e("onCreate dpOpenHelper ", "dpOpenHelper ");
        dpOpenHelper = new ArticleDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dpOpenHelper.getReadableDatabase();
        Cursor cursor;
        Log.e("uriMatcher.article 1 ", "uri 1 " + uri);
        int match = uriMatcher.match(uri);
        Log.e("uriMatcher.article 2 ", "uri 2 " + uri);
        switch (match) {
            case MEMBERS:
                cursor = db.query(ArticleEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            // selection = "_id=?"
            // selectionArgs = 34
            case MEMBER_ID:
                selection = ArticleEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(ArticleEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't query incorrect uri" + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dpOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                long id = db.insert(ArticleEntry.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e("insert metod", "insertion of data in the table failed for " + uri);
                    return null;
                }
                return ContentUris.withAppendedId(uri, id);

            default:
                throw new IllegalArgumentException("insertion of data in the table failed for " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
