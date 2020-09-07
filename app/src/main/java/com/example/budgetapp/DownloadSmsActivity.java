package com.example.budgetapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.budgetapp.Data.BudgetContract.SMSEntry;

import java.util.ArrayList;
import java.util.Date;

public class DownloadSmsActivity extends AppCompatActivity {

    private TextView myTextViewSMS;
    private TextView myTextViewDate;
    private TextView myTextViewSender;
    private TextView myTextViewProcess;
    private TextView myTextViewCard;
    private TextView myTextViewPrice;
    private TextView myTextViewOrganization;
    private TextView myTextViewLimit;

    private EditText editTextSender;
    public ShablonSmsActivity test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Создание шаблонов:");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sms);

        myTextViewSMS = findViewById(R.id.textViewSMS);
        myTextViewDate = findViewById(R.id.textViewDate2);
        myTextViewSender = findViewById(R.id.textViewSender2);
        myTextViewProcess = findViewById(R.id.textViewProcess2);
        myTextViewCard = findViewById(R.id.textViewCard2);
        myTextViewPrice = findViewById(R.id.textViewPrice2);
        myTextViewOrganization = findViewById(R.id.textViewOrganization2);
        myTextViewLimit = findViewById(R.id.textViewLimit2);

        editTextSender = findViewById(R.id.editTextSender);
        ActivityCompat.requestPermissions(DownloadSmsActivity.this, new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

    }

    private void InsertSMS() {
        String date = myTextViewDate.getText().toString().trim();
        String sender = myTextViewSender.getText().toString().trim();
        String process = myTextViewProcess.getText().toString().trim();
        String card = myTextViewCard.getText().toString().trim();
        String price = myTextViewPrice.getText().toString().trim();
        String organization = myTextViewOrganization.getText().toString().trim();
        String limit = myTextViewLimit.getText().toString().trim();

        ContentValues contentValues = new ContentValues();

        contentValues.put(SMSEntry.COLUMNT_DATE, date);
        contentValues.put(SMSEntry.COLUMNT_SENDER, sender);
        contentValues.put(SMSEntry.COLUMNT_PROCESS, process);
        contentValues.put(SMSEntry.COLUMNT_CARD, card);
        contentValues.put(SMSEntry.COLUMNT_PRICE, price);
        contentValues.put(SMSEntry.COLUMNT_ORGANIZATION, organization);
        contentValues.put(SMSEntry.COLUMNT_LIMIT, limit);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(SMSEntry.CONTENT_URI, contentValues);

        if (uri == null) Toast.makeText(this, "Insertion uri btyak", Toast.LENGTH_LONG);
        else Toast.makeText(this, "Data saved", Toast.LENGTH_LONG);
    }

    public void Read_SMS(View view) {


        StringBuilder smsBuilder = new StringBuilder();
        final String SMS_URI_INBOX = "content://sms/inbox";
        String smsSender;
        String textSender = "Google";


        //        перехватываем переменную из editTextSender
/*        try {
            textSender = editTextSender.getText().toString().trim();
            if (textSender == null) textSender = "Google";
        } catch (NumberFormatException e) {
            textSender = "Google";
        }*/
        smsSender = "address LIKE '%" + textSender + "%'";

        try {
            Uri uri = Uri.parse(SMS_URI_INBOX);
            String[] projection = new String[]{/*"_id", */"address", /*"person", */"body", "date"/*, "type"*/};
            Cursor cur = getContentResolver().query(uri, projection, smsSender, null, "date desc");
            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");

                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                do {
                    String strAddress = cur.getString(index_Address);
                    String strbody = cur.getString(index_Body);
                    long longDate = cur.getLong(index_Date);


                    myTextViewSender.setText("" + strAddress);
                    myTextViewDate.setText("" + longDate);
//                    myTextViewDate.setText("" + LongToDate(longDate));
                    myTextViewSMS.setText("Сообщение: " + strbody);
                    String process, organization, card;
                    double price, limit;
                    int nomer, nomer2;
                    nomer = strbody.indexOf(".");
                    if (strbody.substring(0, 7).equals("Покупка")) {
                        process = strbody.substring(0, nomer);
                        myTextViewProcess.setText("" + process);
//                        Log.i("process", "!" + process + "!");
                        nomer = strbody.indexOf("*") + 1;
                        nomer2 = strbody.indexOf(".", nomer);
                        card = strbody.substring(nomer, nomer2);
                        myTextViewCard.setText("" + card);
//                        Log.i("card", "!" + card + "!");
                        nomer = nomer2 + 2;
                        nomer2 = strbody.indexOf("RUB", nomer);
                        price = Double.parseDouble(strbody.substring(nomer, nomer2));
                        myTextViewPrice.setText("" + price);
//                        Log.i("price", "!" + price + "!");
                        nomer = nomer2 + 5;
                        nomer2 = strbody.indexOf(".", nomer);
                        organization = strbody.substring(nomer, nomer2);
                        myTextViewOrganization.setText("" + organization);
//                        Log.i("organization", "!" + organization + "!");
                        nomer = strbody.indexOf("Доступно") + 9;
                        nomer2 = strbody.indexOf("RUB", nomer);
                        limit = Double.parseDouble(strbody.substring(nomer, nomer2));
                        myTextViewLimit.setText("" + limit);
//                        Log.i("limit", "!" + limit + "!");

                        test = new ShablonSmsActivity(longDate, strAddress, process, card, price, organization, limit);
                        test.SetShablonSMS(test);
                    }

                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
                smsBuilder.append("no result!");
            } // end if
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < ShablonSmsActivity.a_new.size(); i++)
            Log.i("SetShablonSMS", "" + test.getShablonSMS(i));
    }

    String StringToDate(String date) { //Преобразование String в удобную дату со временем
        Long dateLong = Long.parseLong(date);
        Date d = new Date(dateLong);
        String s = "" + d.getMonth() + "." + d.getDate() + " " + d.getHours() + ":" + d.getMinutes();
        return s;

    }

    public static String LongToDate(Long date) { //Преобразование Long в удобную дату со временем
        Date d = new Date(date);
        String s = "" + d.getMonth() + "." + d.getDate() + " " + d.getHours() + ":" + d.getMinutes();
        return s;
    }


}