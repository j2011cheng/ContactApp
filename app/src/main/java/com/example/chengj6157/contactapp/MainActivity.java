package com.example.chengj6157.contactapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editAge;
    EditText editAddress;
    EditText searchName;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);


        editName = (EditText) findViewById(R.id.editText_name);
        editAge = (EditText) findViewById(R.id.editText_age);
        editAddress = (EditText) findViewById(R.id.editText_address);
        searchName = (EditText) findViewById(R.id.editText_search);
    }

    public void addData(View v){
        boolean isInserted = myDb.insertData(editName.getText().toString(), editAge.getText().toString(), editAddress.getText().toString());

        if(isInserted){
            Log.d("MyContact", "Successfully inserted " + editName.getText().toString() + " " + editAge.getText().toString() + " " + editAddress.getText().toString());
            Toast.makeText(getApplicationContext(), "Successfully inserted " + editName.getText().toString() + " " + editAge.getText().toString() + " " + editAddress.getText().toString(), Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("MyContact", "Failure inserting data");
            Toast.makeText(getApplicationContext(), "Failure inserting data", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewData(View v){
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        //loop with res using moveToNext
        for(int i = 0; i < res.getColumnCount(); i++){
            buffer.append(res.getColumnName(i));
            buffer.append("\r");
        }
        buffer.append("\n");
        //appent COL to buffer
        res.moveToFirst();
        do{
            for(int j = 0; j < res.getColumnCount(); j++){
                buffer.append(res.getString(j));
                buffer.append("\t");
            }
            buffer.append("\n");
        }while(res.moveToNext());
        //display message using showMessage
        showMessage("Contacts", buffer.toString());
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void searchContact(View v){
        Cursor res = myDb.getAllData();
        res.moveToFirst();
        StringBuffer buffer = new StringBuffer();
        do {
            if(res.getString(res.getColumnIndex("NAME")).equals(searchName.getText().toString())){
                for(int i = 0; i < res.getColumnCount(); i++){
                    buffer.append(res.getString(i));
                    buffer.append(" ");
                }
            }
        }while(res.moveToNext());
        showMessage("Search Contact", buffer.toString());
    }

    public void clearContacts(View v){
        SQLiteDatabase res = myDb.getWritableDatabase();
        res.execSQL("DELETE FROM " + myDb.TABLE_NAME);
        Toast.makeText(getApplicationContext(), "Contacts Cleared", Toast.LENGTH_SHORT);
        myDb.currentID = 1;
        res.close();
    }
}
