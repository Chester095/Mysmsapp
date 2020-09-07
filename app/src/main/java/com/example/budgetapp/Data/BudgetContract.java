package com.example.budgetapp.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class BudgetContract {

    private BudgetContract() {

    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sms";
    public static final int DATABASE_ARTICLE_VERSION = 1;
    public static final String DATABASE_ARTICLE_NAME = "article";

    public static final String SCHEME = "content://";
    public static final String AUTHORITY= "com.example.budgetapp";
    public static final String PATH_MEMBERS = "sms";
    public static final String PATH_MEMBERS_ARTICLE = "article";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    public static final class ArticleEntry implements BaseColumns {

        public static final String TABLE_NAME = "article";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMNT_DATE = "date";
        public static final String COLUMNT_ITEM = "item";
        public static final String COLUMNT_SUBITEM = "subitem";
        public static final String COLUMNT_AMOUNT = "amount";
        public static final String COLUMNT_BILL = "bill";
        public static final String COLUMNT_NOTES = "notes";
        public static final Uri CONTENT_URI_ARTICLE = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS_ARTICLE);

        public static final int BILL_NAL = 0;
        public static final int BILL_TINKOFF = 1;
        public static final int BILL_GPB = 2;
        public static final int BILL_SBER = 3;

        public static final int ITEM_PRODUCT = 0;
        public static final int ITEM_DINNER = 1;
        public static final int ITEM_MUNICIPAL = 2;
        public static final int ITEM_TRANSPORT = 3;

    }

    public static final class SMSEntry implements BaseColumns {

        public static final String TABLE_NAME = "sms";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMNT_DATE = "date";
        public static final String COLUMNT_SENDER = "sender";
        public static final String COLUMNT_PROCESS = "process";
        public static final String COLUMNT_CARD = "card";
        public static final String COLUMNT_PRICE = "price";
        public static final String COLUMNT_ORGANIZATION = "organization";
        public static final String COLUMNT_LIMIT = "limit";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS);
    }

}
