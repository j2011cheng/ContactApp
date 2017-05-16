package com.example.chengj6157.contactapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editAge;
    EditText editAddress;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);


        editName = (EditText) findViewById(R.id.editText_name);
        editAge = (EditText) findViewById(R.id.editText_age);
        editAddress = (EditText) findViewById(R.id.editText_address);
    }

    public void addData(View v){
        boolean isInserted = myDb.insertData(myDb.COL_2, editName.getText().toString()) &&
                myDb.insertData(myDb.COL_3, editAge.getText().toString()) &&
                myDb.insertData(myDb.COL_4, editAddress.getText().toString());

        if(isInserted == true){
            Log.d("MyContact", "Successfully inserted " + editName.getText().toString() + " " + editAge.getText().toString() + " " + editAddress.getText().toString());
        }
        else{
            Log.d("MyContact", "Failure inserting data");
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
        //appent COL to buffer
        //display message using showMessage
        showMessage("Data", buffer.toString());
    }

    private void showMessage(String title, String s) {
        //AlertDialog.Builder
    }
}
