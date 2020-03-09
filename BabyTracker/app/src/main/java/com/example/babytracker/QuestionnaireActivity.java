package com.example.babytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionnaireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context showConfirmation = getApplicationContext();
                CharSequence confirmationText = "Submitted";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(showConfirmation, confirmationText, duration);
                toast.show();
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 350, 350);
            }
        });
    }
}
