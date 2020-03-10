package com.example.babytracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

<<<<<<< HEAD
import com.amazonaws.amplify.generated.graphql.CreateBabyMutation;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.List;

import javax.annotation.Nonnull;

import type.CreateBabyInput;
=======
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
>>>>>>> deb0c066136e09858b1cdb35128d867f7a427cb6

public class QuestionnaireActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    private AWSAppSyncClient mAWSAppSyncClient;
    List<Baby> babyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

<<<<<<< HEAD
        //This is from the amplify doc to enable us to integrate into our app
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

=======
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("daylongTheGreat", "onResult: " + userStateDetails.getUserState());
                        if (userStateDetails.getUserState().toString().equals("SIGNED_OUT")) {
                            AWSMobileClient
                                    .getInstance()
                                    .showSignIn(QuestionnaireActivity.this, SignInUIOptions.builder().build(),
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
>>>>>>> deb0c066136e09858b1cdb35128d867f7a427cb6

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Context showConfirmation = getApplicationContext();
//                CharSequence confirmationText = "Submitted";
//                int duration = Toast.LENGTH_LONG;
//                Toast toast = Toast.makeText(showConfirmation, confirmationText, duration);
//                toast.show();
//                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 350, 350);
//                String inputText =  input.getText().toString();
//                runMutation(inputText);
            }
        });
    }

<<<<<<< HEAD
    //connecting to dynamo db
    public void runMutation(String name, String dob, Boolean immunization){
        CreateBabyInput createBabyInput = CreateBabyInput.builder()
                .name(name)
                .dob(dob)
                .immunization(immunization)
                .build();
        mAWSAppSyncClient.mutate(CreateBabyMutation.builder().input(createBabyInput).build())
                .enqueue(addMutationCallback);
    }

    private GraphQLCall.Callback<CreateBabyMutation.Data> addMutationCallback = new GraphQLCall.Callback<CreateBabyMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateBabyMutation.Data> response) {
            Log.i("Results", "Added Todo");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };
=======
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("daylongTheGreat", "onResume");

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("daylongTheGreat", "onResult: " + userStateDetails.getUserState());
                        if (userStateDetails.getUserState().toString().equals("SIGNED_OUT")) {
                            AWSMobileClient
                                    .getInstance()
                                    .showSignIn(QuestionnaireActivity.this, SignInUIOptions.builder().build(),
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

        } else if (itemId == R.id.widget_to_settings) {
            Intent goToAllTask = new Intent (this, FeedingActivity.class);
            this.startActivity(goToAllTask);
            return (true);

        } else if (itemId == R.id.logout_button) {
            Toast.makeText(QuestionnaireActivity.this, "Logging Out User", Toast.LENGTH_LONG).show();
            AWSMobileClient.getInstance().signOut();
            finish();
        }
        return(super.onOptionsItemSelected(item));
    }

>>>>>>> deb0c066136e09858b1cdb35128d867f7a427cb6
}
