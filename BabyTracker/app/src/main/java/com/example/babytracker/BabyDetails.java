package com.example.babytracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressLint("Registered")
public class BabyDetails extends AppCompatActivity {
    List<Baby> dataSet = new ArrayList<>();
    private AWSAppSyncClient awsSyncer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        //
        awsSyncer = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        ImageView baby_image = findViewById(R.id.baby_image);

        String pathOnS3 = "https://bucketfortasks123331-taskenv.s3-us-west-2.amazonaws.com/public/public/c803c223-3ea7-46ca-ba0e-1dfc9f14de7d";
        Picasso.get().load(pathOnS3).into(baby_image);

        Intent showBabyID = getIntent();
        String showBabyName = showBabyID.getStringExtra("baby_name");
        TextView showBabyName2 = findViewById(R.id.baby_name);

        String showDOBName = showBabyID.getStringExtra("baby_dob");
        TextView showDOBName2 = findViewById(R.id.date_of_birth);


        showBabyName2.setText(showBabyName);
        showDOBName2.setText(showDOBName);

    }

    @Override
    protected void onResume() {
        super.onResume();
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("daylongTheGreat", "onResult: " + userStateDetails.getUserState());
                        if (userStateDetails.getUserState().toString().equals("SIGNED_OUT")) {
                            AWSMobileClient
                                    .getInstance()
                                    .showSignIn(BabyDetails.this, SignInUIOptions.builder().build(),
                                            new Callback<UserStateDetails>() {
                                                @Override
                                                public void onResult(UserStateDetails result) {
                                                    Log.d("daylongTheGreat", "onResult: " + result.getUserState());
                                                    switch (result.getUserState()) {
                                                        case SIGNED_IN:
                                                            Log.i("INIT", "logged in!");
                                                            break;
                                                        case SIGNED_OUT:
                                                            Log.i("daylongTheGreat", "ERROR: User did not choose to sign-in");
                                                            break;
                                                        default:
                                                            AWSMobileClient.getInstance().signOut();
                                                            break;
                                                    }
                                                }

                                                @Override
                                                public void onError(Exception e) {
                                                }
                                            });
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("daylongTheGreat", "_____ERROR_____ " + e.toString());
                    }
                }
        );
    }

    // Allow nav_and_actions to be utilized
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.widget_to_main) {
            Intent goToMain = new Intent(this, MainActivity.class);
            this.startActivity(goToMain);
            return (true);
        } else if (itemId == R.id.widget_to_profile) {
            Intent goToAddTask = new Intent(this, QuestionnaireActivity.class);
            this.startActivity(goToAddTask);
            return (true);
        } else if (itemId == R.id.widget_to_settings) {
            Intent goToAllTask = new Intent(this, FeedingActivity.class);
            this.startActivity(goToAllTask);
            return (true);
        } else if (itemId == R.id.logout_button) {
            Toast.makeText(BabyDetails.this, "Logging Out User", Toast.LENGTH_LONG).show();
            AWSMobileClient.getInstance().signOut();
            finish();
        }
        return (super.onOptionsItemSelected(item));
    }
}