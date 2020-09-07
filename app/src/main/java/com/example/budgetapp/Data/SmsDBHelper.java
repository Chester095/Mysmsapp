package com.example.budgetapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.budgetapp.Data.BudgetContract.SMSEntry;

public class SmsDBHelper extends SQLiteOpenHelper {
    public SmsDBHelper(Context context) {
        super(context, BudgetContract.DATABASE_NAME, null, BudgetContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL - Structured Query Language
        String CREATE_SMS_TABLE = "CREATE TABLE " + SMSEntry.TABLE_NAME + "("
                + SMSEntry._ID + " INTEGER PRIMARY KEY,"
                + SMSEntry.COLUMNT_DATE + " TEXT,"
                + SMSEntry.COLUMNT_SENDER + " TEXT,"
                + SMSEntry.COLUMNT_PROCESS + " TEXT,"
                + SMSEntry.COLUMNT_CARD + " TEXT,"
                + SMSEntry.COLUMNT_PRICE + " TEXT,"
                + SMSEntry.COLUMNT_ORGANIZATION + " TEXT,"
                + SMSEntry.COLUMNT_LIMIT + "TEXT" + ")";

        db.execSQL(CREATE_SMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BudgetContract.DATABASE_NAME);
        onCreate(db);
    }

}
