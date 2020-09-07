package com.example.budgetapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetapp.Data.BudgetContract.ArticleEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainBudgetActivity extends AppCompatActivity {

    TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Бюджет");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_budget);

        dataTextView = findViewById(R.id.dataTextView);

        ImageButton imageButtonmageButtonSMS = findViewById(R.id.imageButtonSMS);
        imageButtonmageButtonSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainBudgetActivity.this, DownloadSmsActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton floatingActionButton =
                findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBudgetActivity.this,
                        AddArticleActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void displayData() {
        String[] projection = {
                ArticleEntry._ID, ArticleEntry.COLUMNT_DATE, ArticleEntry.COLUMNT_ITEM, ArticleEntry.COLUMNT_SUBITEM, ArticleEntry.COLUMNT_AMOUNT,
                ArticleEntry.COLUMNT_BILL, ArticleEntry.COLUMNT_NOTES
        };
        Log.e("projection", "projection " + projection);
        Log.e("CONTENT_URI_ARTICLE 1 ", "CONTENT_URI_ARTICLE 1 " + ArticleEntry.CONTENT_URI_ARTICLE);
        Cursor cursor = getContentResolver().query(ArticleEntry.CONTENT_URI_ARTICLE, projection, null, null, null);
        Log.e("CONTENT_URI_ARTICLE 2 ", "CONTENT_URI_ARTICLE 2 " + ArticleEntry.CONTENT_URI_ARTICLE);

        dataTextView.setText("All articles \n\n");
        dataTextView.append(ArticleEntry._ID + " " + ArticleEntry.COLUMNT_DATE + " " + ArticleEntry.COLUMNT_ITEM + " " + ArticleEntry.COLUMNT_SUBITEM + " " + ArticleEntry.COLUMNT_AMOUNT + " " +
                ArticleEntry.COLUMNT_BILL + " " + ArticleEntry.COLUMNT_NOTES);
        Log.e("CONTENT_URI_ARTICLE 3 ", "CONTENT_URI_ARTICLE 3 " + ArticleEntry.CONTENT_URI_ARTICLE);

        int idIndex = cursor.getColumnIndex(ArticleEntry._ID);
        int idDate = cursor.getColumnIndex(ArticleEntry.COLUMNT_DATE);
        int idItem = cursor.getColumnIndex(ArticleEntry.COLUMNT_ITEM);
        int idSubitem = cursor.getColumnIndex(ArticleEntry.COLUMNT_SUBITEM);
        int idAmount = cursor.getColumnIndex(ArticleEntry.COLUMNT_AMOUNT);
        int idBill = cursor.getColumnIndex(ArticleEntry.COLUMNT_BILL);
        int idNotes = cursor.getColumnIndex(ArticleEntry.COLUMNT_NOTES);

        while (cursor.moveToNext()) {
            int currentId = cursor.getInt(idIndex);
            String currentDate = cursor.getString(idDate);
            int currentItem = cursor.getInt(idItem);
            String currentSubitem = cursor.getString(idSubitem);
            String currentAmount = cursor.getString(idAmount);
            int currentBill = cursor.getInt(idBill);
            String currentNotes = cursor.getString(idNotes);

            dataTextView.append("\n" + currentId + " " + currentDate + " " + currentItem + " " + currentSubitem + " " + currentAmount + " " + currentBill + " " + currentNotes);
            cursor.close();
        }
    }
}