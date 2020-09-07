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

import com.example.budgetapp.Data.BudgetContract.SMSEntry;


public class SmsContentProvider extends ContentProvider {

    SmsDBHelper dpOpenHelper_sms;
    private static final int MEMBERS = 223, MEMBER_ID = 333;

    private static final UriMatcher uriMatcher_sms = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher_sms.addURI(BudgetContract.AUTHORITY, BudgetContract.PATH_MEMBERS, MEMBERS);
        uriMatcher_sms.addURI(BudgetContract.AUTHORITY, BudgetContract.PATH_MEMBERS + "/#", MEMBER_ID);
    }

    @Override
    public boolean onCreate() {
        dpOpenHelper_sms = new SmsDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db_sms = dpOpenHelper_sms.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher_sms.match(uri);
        Log.e("match", "uriMatcher_sms.match(uri) " + match);
        Log.e("uriMatcher.match SMS", "uri SMS " + uri);
        switch (match) {
            case MEMBERS:
                cursor = db_sms.query(SMSEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            // selection = "_id=?"
            // selectionArgs = 34
            case MEMBER_ID:
                selection = SMSEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db_sms.query(SMSEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't query incorrect uri" + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dpOpenHelper_sms.getWritableDatabase();
        int match = uriMatcher_sms.match(uri);

        switch (match) {
            case MEMBERS:
                long id = db.insert(SMSEntry.TABLE_NAME, null, values);
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
