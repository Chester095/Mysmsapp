package com.example.budgetapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.budgetapp.Data.BudgetContract.ArticleEntry;

public class ArticleDBHelper extends SQLiteOpenHelper {
    public ArticleDBHelper(Context context) {
        super(context, BudgetContract.DATABASE_ARTICLE_NAME, null, BudgetContract.DATABASE_ARTICLE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL - Structured Query Language
        String CREATE_SMS_TABLE = "CREATE TABLE " + ArticleEntry.TABLE_NAME + "("
                + ArticleEntry._ID + " INTEGER PRIMARY KEY,"
                + ArticleEntry.COLUMNT_DATE + " TEXT,"
                + ArticleEntry.COLUMNT_ITEM + " INTEGER NOT NULL,"
                + ArticleEntry.COLUMNT_SUBITEM + " TEXT,"
                + ArticleEntry.COLUMNT_AMOUNT + " TEXT,"
                + ArticleEntry.COLUMNT_BILL + " INTEGER NOT NULL,"
                + ArticleEntry.COLUMNT_NOTES + " TEXT" + ")";

        db.execSQL(CREATE_SMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BudgetContract.DATABASE_NAME);
        onCreate(db);
    }

}
