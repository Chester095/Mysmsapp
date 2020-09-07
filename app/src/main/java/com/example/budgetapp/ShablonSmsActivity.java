package com.example.budgetapp;

import java.util.ArrayList;

public class ShablonSmsActivity extends DownloadSmsActivity {
    String sender, process, organization, card;
    double price, limit;
    Long date;
    public static ArrayList<ShablonSmsActivity> a_new = new ArrayList<>();

    public ShablonSmsActivity(Long date, String sender, String process, String card, double price, String organization, double limit) {
        this.date = date;
        this.sender = sender;
        this.process = process;
        this.card = card;
        this.price = price;
        this.organization = organization;
        this.limit = limit;
    }

    public String getShablonSMS(int i) {
        String b;
        return b = "i=" + i + "   " + LongToDate(a_new.get(i).date) + "  " + a_new.get(i).sender + "  " + a_new.get(i).process + "  " + a_new.get(i).card + "  " + a_new.get(i).price + "  " + a_new.get(i).organization + "  " + a_new.get(i).limit;
    }

    public void SetShablonSMS(ShablonSmsActivity a) {
        if (a_new != null && !a_new.isEmpty()) {
            if (a.date < a_new.get(a_new.size() - 1).date)
                a_new.add(a);
        } else a_new.add(a);
    }

}
