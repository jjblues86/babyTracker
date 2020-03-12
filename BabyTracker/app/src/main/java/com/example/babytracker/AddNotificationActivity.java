package com.example.babytracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;

public class AddNotificationActivity extends AppCompatActivity {
    String TAG = "AddNotificationActivity";
    public static final String CHANNEL_ID = "Set_Notification";
    private NotificationManagerCompat notificationManagerCompat;
    private EditText notificationTime;
    private EditText notificationDate;

    private AWSAppSyncClient mAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotification);

        notificationManagerCompat = NotificationManagerCompat.from(this);
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


        // Create an explicit intent for an Activity in your app
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//            //creating notifications
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
////                    .setSmallIcon(R.drawable.notification_icon)
////                    .setContentTitle(textTitle)
////                    .setContentText(textContent)
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//            // notificationId is a unique int for each notification that you must define
//            notificationManager.notify((int)(Math.random() * 100.0), builder.build());


    }

    public void setNotification(View view){
        String time = notificationTime.getText().toString();
        String date = notificationDate.getText().toString();
        //creating notifications
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_plus_one_black_24dp)
                .setContentTitle(time)
                .setContentText(date)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(1, notification);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
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
}

