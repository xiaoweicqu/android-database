package com.example.simpledatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DatabaseExample";

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create database.
        databaseHelper = new DatabaseHelper(this, "BookStore.db", null, 1);
        Button createDB = (Button) findViewById(R.id.create_db);
        createDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.getWritableDatabase();
            }
        });

        // Adds data.
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                // Adds the first item.
                values.put("name", "Efficient Android Threading");
                values.put("author", "Anders Goransson");
                values.put("pages", "280");
                values.put("price", "49.99");
                db.insert("Book", null, values);
                values.clear();
                // Adds the second item.
                values.put("name", "Android Programming");
                values.put("author", "Bill Phillips;Chris Stewart;Brian Hardy;Kristin Marsicano");
                values.put("pages", "568");
                values.put("price", "109");
                db.insert("Book", null, values);

            }
        });

        // Update data.
        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", "108");
                db.update("Book", values, "name = ?", new String[]{"Android Programming"});
            }
        });

        // Delete Data.
        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[]{"500"});
            }
        });

        // Query Data.
        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                // Query all books in the database.
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                printData(cursor);
                cursor.close();
            }
        });
    }

    private void printData(Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                // Interval Cursor to print all data
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Log.i(TAG, "Book: " + name);
                Log.i(TAG, "Author: " + author);
                Log.i(TAG, "Pages: " + pages);
                Log.i(TAG, "Price: " + price);
                Log.i(TAG, "");
            } while (cursor.moveToNext());
        }
    }
}
