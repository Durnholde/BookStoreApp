package com.example.android.bookstoreapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.bookstoreapp.data.BookDbHelper;
import com.example.android.bookstoreapp.data.BookContract.BookEntry;

public class MainActivity extends AppCompatActivity {
    private static final int BOOK_LOADER_ID = 0;
    BookCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCursorAdapter = new BookCursorAdapter(this, null);

        ListView bookListView = findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyView);
        bookListView.setAdapter(mCursorAdapter);

        // TODO: ONCLISKLISTENERS!!!! ! !!!!!

        getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);

        //TODO: FLOATING ADD BUTTON !!!!!!!

        insertPlaceholderData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void insertPlaceholderData() {
        SQLiteDatabase database = mBookDbHelper.getWritableDatabase();

        ContentValues insertBook = new ContentValues();
        insertBook.put(BookEntry.COLUMN_BOOK_NAME, "Book 1");
        insertBook.put(BookEntry.COLUMN_BOOK_PRICE, 39);
        insertBook.put(BookEntry.COLUMN_BOOK_QUANTITY, 12);
        insertBook.put(BookEntry.COLUMN_BOOK_SUPPLIER, "Book 1 Supplier");
        insertBook.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE, "123-456-789");
        long newID = database.insert(BookEntry.TABLE_NAME, null, insertBook);

        insertBook.put(BookEntry.COLUMN_BOOK_NAME, "Book 2");
        insertBook.put(BookEntry.COLUMN_BOOK_PRICE, 5);
        insertBook.put(BookEntry.COLUMN_BOOK_QUANTITY, 1189);
        insertBook.put(BookEntry.COLUMN_BOOK_SUPPLIER, "Book 2 Supplier");
        insertBook.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE, "123-456-789");
        newID = database.insert(BookEntry.TABLE_NAME, null, insertBook);

        insertBook.put(BookEntry.COLUMN_BOOK_NAME, "Book 3");
        insertBook.put(BookEntry.COLUMN_BOOK_PRICE, 59);
        insertBook.put(BookEntry.COLUMN_BOOK_QUANTITY, 56);
        insertBook.put(BookEntry.COLUMN_BOOK_SUPPLIER, "Book 3 Supplier");
        insertBook.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE, "123-456-789");
        newID = database.insert(BookEntry.TABLE_NAME, null, insertBook);
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mBookDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_BOOK_SUPPLIER,
                BookEntry.COLUMN_BOOK_SUPPLIER_PHONE};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_book);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The books table contains " + cursor.getCount() + " books.\n\n");
            displayView.append(BookEntry._ID + " - " +
                    BookEntry.COLUMN_BOOK_NAME + " - " +
                    BookEntry.COLUMN_BOOK_PRICE + " - " +
                    BookEntry.COLUMN_BOOK_QUANTITY + " - " +
                    BookEntry.COLUMN_BOOK_SUPPLIER + " - " +
                    BookEntry.COLUMN_BOOK_SUPPLIER_PHONE + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER);
            int supplierNumberColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplier = cursor.getString(supplierColumnIndex);
                String currentSupplierNumber = cursor.getString(supplierNumberColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplier + " - " +
                        currentSupplierNumber));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}
