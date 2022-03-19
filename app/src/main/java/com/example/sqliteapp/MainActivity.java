package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqliteapp.db.DBHelper;
import com.example.sqliteapp.model.User;

public class MainActivity extends AppCompatActivity {

    private EditText username_et, occupation_et;
    private Button saveButton, updateButton, readButton, deleteButton, readAllButton;
    private TextView detailsView;
    private String username, occupation;


    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_et = findViewById(R.id.enterUsername_et);
        occupation_et = findViewById(R.id.enterOccupation_et);

        saveButton = findViewById(R.id.saveButton);
        updateButton = findViewById(R.id.updateButton);
        readButton = findViewById(R.id.readButton);
        deleteButton = findViewById(R.id.deleteButton);
        readAllButton = findViewById(R.id.readAllButton);

        detailsView = findViewById(R.id.detailsView);


        dbHelper = new DBHelper(getApplicationContext());

        setListeners();
    }

    private void setListeners() {
        saveButton.setOnClickListener(v -> {
            if(Validations(username_et.getText().toString().trim(), occupation_et.getText().toString().trim())) {
                User user = new User(username, occupation);
                dbHelper.insertData(user);
                showToast("User Data has been added");
            }
        });

        updateButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(username_et.getText().toString().trim())) {
                User user = new User(username_et.getText().toString().trim(), occupation_et.getText().toString().trim());
                dbHelper.updateData(user.getUsername(), user);
                showToast("User Details updated!!");
            }
        });

        deleteButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(username_et.getText().toString().trim())) {
                dbHelper.deleteData(username_et.getText().toString().trim());
                showToast("User Data deleted!!");
            }
        });
        readButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(username_et.getText().toString().trim())) {
                Cursor cursor = dbHelper.getUserData(username_et.getText().toString().trim());
                if (cursor.getCount() == 0) {
                    showToast("User does not exists!!");
                } else {
                    username = cursor.getString(0);
                    occupation = cursor.getString(1);
                    String text = "Username: " + username + "\n" + "Occupation: " + occupation;
                    detailsView.setText(text);
                }
            }
        });
        readAllButton.setOnClickListener(v -> {
            StringBuilder sb = new StringBuilder();
            Cursor cursor = dbHelper.readAllData();
            do{
                sb.append("Username: ").append(cursor.getString(0)).append("\n").append("Occupation: ").append(cursor.getString(1)).append("\n\n\n");
            }while (cursor.moveToNext());
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("All Users")
                    .setMessage(sb.toString())
                    .setPositiveButton("OK", (dialog1, which) -> dialog1.dismiss())
                    .create();

            dialog.show();
        });
    }
    private Boolean Validations(String username, String occupation) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(occupation)) {
            this.username = username;
            this.occupation = occupation;
            return true;
        } else {
            showToast("Enter in All the fields!!");
            return false;
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}