package com.example.budgetapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.budgetapp.Data.BudgetContract.ArticleEntry;
import com.example.budgetapp.Data.BudgetContract.SMSEntry;

public class AddArticleActivity extends AppCompatActivity {

    private EditText editTextDate;
    private Spinner spinnerItem;
    private EditText editTextSubitem;
    private EditText editTextAmount;
    private Spinner spinnerBill;
    private EditText editTextNotes;
    private int item = 0, bill = 0;
    private ArrayAdapter spinnerAdapterItem, spinnerAdapterBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Добавить статью");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        editTextDate = findViewById(R.id.editTextDate);
        editTextSubitem = findViewById(R.id.editTextSubitem);
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextNotes = findViewById(R.id.editTextNotes);
        spinnerItem = findViewById(R.id.spinnerItem);
        spinnerBill = findViewById(R.id.spinnerBill);


        spinnerAdapterItem = ArrayAdapter.createFromResource(this, R.array.array_item, android.R.layout.simple_spinner_item);
        spinnerAdapterItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItem.setAdapter(spinnerAdapterItem);

        spinnerAdapterBill = ArrayAdapter.createFromResource(this, R.array.array_bill, android.R.layout.simple_spinner_item);
        spinnerAdapterBill.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBill.setAdapter(spinnerAdapterBill);

        spinnerBill.setOnItemSelectedListener
                (new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        if (!TextUtils.isEmpty(selectedItem)) {
                            if (selectedItem.equals("Тинькофф *9048")) { bill = ArticleEntry.BILL_TINKOFF;
                            } else if (selectedItem.equals("Газпромбанк *8589")) { bill = ArticleEntry.BILL_GPB;
                            } else if (selectedItem.equals("Сбербанк *8000")) { bill = ArticleEntry.BILL_SBER;
                            } else { bill = ArticleEntry.BILL_NAL; }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        bill = 0;
                    }
                });

        spinnerItem.setOnItemSelectedListener
                (new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        if (!TextUtils.isEmpty(selectedItem)) {
                            if (selectedItem.equals("Обед")) { item = ArticleEntry.ITEM_DINNER;
                            } else if (selectedItem.equals("ЖКХ")) { item = ArticleEntry.ITEM_MUNICIPAL;
                            } else if (selectedItem.equals("Транспорт")) { item = ArticleEntry.ITEM_TRANSPORT;
                            } else { item = ArticleEntry.ITEM_PRODUCT;}
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { item = 0;}
                });

    }

    private void InsertArticle() {
        String textDate = editTextDate.getText().toString().trim(); //trim обрезает пробелы в начале и конце строки
        String textSubitem = editTextSubitem.getText().toString().trim();
        String textAmount = editTextAmount.getText().toString().trim();
        String textNotes = editTextNotes.getText().toString().trim();


        ContentValues contentValues = new ContentValues();

        contentValues.put(ArticleEntry.COLUMNT_DATE, textDate);
        contentValues.put(ArticleEntry.COLUMNT_ITEM, item);
        contentValues.put(ArticleEntry.COLUMNT_SUBITEM, textSubitem);
        contentValues.put(ArticleEntry.COLUMNT_AMOUNT, textAmount);
        contentValues.put(ArticleEntry.COLUMNT_BILL, bill);
        contentValues.put(ArticleEntry.COLUMNT_NOTES, textNotes);

        ContentResolver contentResolver = getContentResolver();  //класс который определяет какой контенет провайдер использовать
        Uri uri = contentResolver.insert(ArticleEntry.CONTENT_URI_ARTICLE, contentValues);

        if (uri == null) Toast.makeText(this, "Insertion uri btyak", Toast.LENGTH_LONG);
        else Toast.makeText(this, "Data saved", Toast.LENGTH_LONG);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.edit_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                return true;
            case R.id.delete_item:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
//        return super.onOptionsItemSelected(item);
    }


/*
    private void insertArticle(){
        String
    }*/
}