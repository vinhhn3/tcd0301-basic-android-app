package com.example.tcd0301basicandroidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name, contact, dob;
    Button btnInsert, btnView, btnDelete, btnUpdate;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);

        btnInsert = findViewById(R.id.btnInsert);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnView = findViewById(R.id.btnView);

        db = new DBHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                String contactText = contact.getText().toString();
                String dobText = dob.getText().toString();
                if (!nameText.isEmpty() && !contactText.isEmpty() && !dobText.isEmpty()){
                    Boolean insertedDataSuccessful = db.insertUserData(nameText,
                            contactText, dobText);
                    if (insertedDataSuccessful){
                        Toast.makeText(MainActivity.this, "Inserted Successfully !!!",
                                Toast.LENGTH_LONG
                        ).show();
                        name.setText("");
                        contact.setText("");
                        dob.setText("");
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Inserted Failed !!!",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Please enter all inputs !!!",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.getData();
                if (cursor.getCount() == 0){
                    Toast.makeText(MainActivity.this, "NO ENTRY", Toast.LENGTH_LONG
                    ).show();
                }
                else {
                    StringBuffer buffer = new StringBuffer();
                    while (cursor.moveToNext()){
                        buffer.append("Name:" + cursor.getString(0) + "\n");
                        buffer.append("Contact:" + cursor.getString(1) + "\n");
                        buffer.append("Date of Birth:" + cursor.getString(2) + "\n\n");
                    }

//                    Toast.makeText(MainActivity.this, buffer.toString(),
//                            Toast.LENGTH_LONG
//                    ).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("User Entries");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }
            }
        });

       btnDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String nameText = name.getText().toString();

               Boolean result = db.deleteUserData(nameText);

               if (result){
                   Toast.makeText(MainActivity.this, "User deleted ...", Toast.LENGTH_LONG
                   ).show();
               }
               else{
                   Toast.makeText(MainActivity.this, "User NOT FOUND ...", Toast.LENGTH_LONG
                   ).show();
               }
           }
       });
    }
}