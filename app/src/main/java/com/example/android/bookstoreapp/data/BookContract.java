package com.example.android.bookstoreapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class BookContract
{
    public static final String CONTENT_AUTHORITY = "com.example.android.bookstoreapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOKS = "books";

    private BookContract(){}

    public static final class BookEntry implements BaseColumns
    {
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        public final static String TABLE_NAME = "books";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_BOOK_NAME = "Product_Name";
        public final static String COLUMN_BOOK_PRICE = "Price";
        public final static String COLUMN_BOOK_QUANTITY = "Quantity";
        public final static String COLUMN_BOOK_SUPPLIER = "Supplier_Name";
        public final static String COLUMN_BOOK_SUPPLIER_PHONE = "Supplier_Phone_Number";
    }
}
