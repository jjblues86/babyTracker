package com.example.babytracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.amplify.generated.graphql.ListBabysQuery;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    private AWSAppSyncClient mAWSAppSyncClient;
    private static PinpointManager pinpointManager;
    List<Baby> babyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize PinpointManager
        getPinpointManager(getApplicationContext());

        //This is from the amplify doc to enable us to integrate into our app
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        getBabyItems();

        this.babyList = new ArrayList<>();

        for(Baby baby : babyList){
            Log.i(TAG, baby.name + baby.dateOfBirth);
        }


        RecyclerView recyclerView = findViewById(R.id.babies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyBabyRecyclerViewAdapter(this.babyList, null));

        //Button to take you the questionnaire activity
        final Button questionnaireButton = findViewById(R.id.addBaby);
        questionnaireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToQuestionnaire = new Intent(MainActivity.this, QuestionnaireActivity.class);
                MainActivity.this.startActivity(goToQuestionnaire);
            }
        });

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.getUserState());

                        if(userStateDetails.getUserState().equals(UserState.SIGNED_OUT)) {
                            AWSMobileClient.getInstance().showSignIn(MainActivity.this, new Callback<UserStateDetails>() {
                                @Override
                                public void onResult(UserStateDetails result) {
                                    Log.d(TAG, "onResult: " + result.getUserState());
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e(TAG, "onError: ", e);
                                }
                            });

                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );

    }

    //this method enables me to query data stored in dynamodb to render on my front page
    public void getBabyItems()
    {
        Log.i(TAG, "Did we make it into getTaskItems");

        mAWSAppSyncClient.query(ListBabysQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.NETWORK_ONLY)
                .enqueue(babyCallback);
    }
    private GraphQLCall.Callback<ListBabysQuery.Data> babyCallback = new GraphQLCall.Callback<ListBabysQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<ListBabysQuery.Data> response)
        {
            Log.i(TAG, response.data().listBabys().items().toString());

            if(babyList.size() == 0 || response.data().listBabys().items().size() != babyList.size()){

                babyList.clear();

                for(ListBabysQuery.Item item : response.data().listBabys().items()){
                    Baby addBaby = new Baby(item.name(), item.dob());
                    babyList.add(addBaby);
                }
                Handler handler = new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message inputMessage){
                        RecyclerView recyclerView = findViewById(R.id.babies);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                };
                handler.obtainMessage().sendToTarget();
            }
        }
        @Override
        public void onFailure(@Nonnull ApolloException e)
        {
            Log.e(TAG, e.toString());
//            taskDatabase.taskDao().getAll();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("daylongTheGreat", "onResult: " + userStateDetails.getUserState());
                        if (userStateDetails.getUserState().toString().equals("SIGNED_OUT")) {
                            AWSMobileClient
                                    .getInstance()
                                    .showSignIn(MainActivity.this, SignInUIOptions.builder().build(),
                                            new Callback<UserStateDetails>() {
                                                @Override
                                                public void onResult(UserStateDetails result) {
                                                    Log.d("daylongTheGreat", "onResult: " + result.getUserState());
                                                    switch (result.getUserState()){
                                                        case SIGNED_IN:
                                                            Log.i("INIT", "LOGGED IN");
                                                            break;
                                                        case SIGNED_OUT:
                                                            Log.i("daylongTheGreat", "_____ERROR: NO SIGN IN_____");
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onCreate");

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
            Intent goToMain = new Intent (this, MainActivity.class);
            this.startActivity(goToMain);
            return (true);

        } else if (itemId == R.id.widget_to_profile) {
            Intent goToAddTask = new Intent (this, QuestionnaireActivity.class);
            this.startActivity(goToAddTask);
            return (true);

        } else if (itemId == R.id.widget_to_notification) {
            Intent goToNotification = new Intent (this, AddNotificationActivity.class);
            this.startActivity(goToNotification);
            return (true);

        } else if (itemId == R.id.logout_button) {
            Toast.makeText(MainActivity.this, "Logging Out User", Toast.LENGTH_LONG).show();
            AWSMobileClient.getInstance().signOut();
            finish();
        }
        return(super.onOptionsItemSelected(item));
    }

    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i("INIT", userStateDetails.getUserState().toString());
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        public String TAG = "MAIN.PINPOINT";

                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }
                            final String token = task.getResult().getToken();
                            Log.d(TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);

                        }

//                        @Override
//                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                            if (!task.isSuccessful()) {
//                                Log.w(TAG, "getInstanceId failed", task.getException());
//                                return;
//                            }
//                            final String token = task.getResult().getToken();
//                            Log.d(TAG, "Registering push notifications token: " + token);
//                            pinpointManager.getNotificationClient().registerDeviceToken(token);
//                        }
                    });
        }
        return pinpointManager;
    }
}

