package com.example.babytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotification);

        Spinner notifications = (Spinner) findViewById(R.id.spinner);
        //ArrayAdapter is the container that will hold the values and integrate them with spinner
        ArrayAdapter<String> babyAdapter = new ArrayAdapter<String>(AddNotificationActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        babyAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        notifications.setAdapter(babyAdapter);
    }
}
