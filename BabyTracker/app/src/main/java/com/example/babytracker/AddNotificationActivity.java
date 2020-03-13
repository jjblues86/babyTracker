package com.example.babytracker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
public class AddNotificationActivity extends AppCompatActivity {
    String TAG = "AddNotificationActivity";
    public static final String CHANNEL_ID = "Set_Notification";
    private NotificationManagerCompat notificationManagerCompat;
    private EditText notificationTime;
    private EditText notificationDate;
    private Spinner notificationSpinner;
    private AWSAppSyncClient mAWSAppSyncClient;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationSpinner = findViewById(R.id.spinner);
        notificationTime = findViewById(R.id.date);
        notificationDate = findViewById(R.id.time);
        //This is from the amplify doc to enable us to integrate into our app
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        //This creates the dropdown menu utilizing the Spinner
        Spinner notifications = (Spinner) findViewById(R.id.spinner);
        //ArrayAdapter is the container that will hold the values and integrate them with spinner
        ArrayAdapter<String> babyAdapter = new ArrayAdapter<String>(AddNotificationActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        babyAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        notifications.setAdapter(babyAdapter);
        createNotificationChannel();
    }
    public void setNotification(View view){
        int id = (int) (Math.random() * 100.0);
        String spinner = notificationSpinner.getSelectedItem().toString();
        String time = notificationTime.getText().toString();
        String date = notificationDate.getText().toString();
        //creating notifications
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_plus_one_black_24dp)
//                .setContentTitle(spinner)
                .setContentTitle(time)
                .setContentText(spinner)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)//if device is in dono disturb mode
                .build();
        notificationManagerCompat.notify(id, notification);
        Intent gotBackToMain =new Intent(this, MainActivity.class);
        this.startActivity(gotBackToMain);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "set notification",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("This is to set notification");
//            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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

        } else if (itemId == R.id.widget_to_location) {
            Intent goToLocation = new Intent(this, ImmunizationMapsActivity2.class);
            this.startActivity(goToLocation);
            return (true);

        } else if (itemId == R.id.widget_to_settings) {
            Intent goToAllTask = new Intent(this, FeedingActivity.class);
            this.startActivity(goToAllTask);
            return (true);


        } else if (itemId == R.id.widget_to_notification) {
            Intent goToNotification = new Intent (this, AddNotificationActivity.class);
            this.startActivity(goToNotification);
            return (true);

        } else if (itemId == R.id.logout_button) {
            Toast.makeText(AddNotificationActivity.this, "Logging Out User", Toast.LENGTH_LONG).show();
            AWSMobileClient.getInstance().signOut();
            finish();
        }
        return (super.onOptionsItemSelected(item));
    }
}