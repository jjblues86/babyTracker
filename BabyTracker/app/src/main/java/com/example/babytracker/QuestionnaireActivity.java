package com.example.babytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.CreateBabyMutation;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.List;

import javax.annotation.Nonnull;

import type.CreateBabyInput;

public class QuestionnaireActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    private AWSAppSyncClient mAWSAppSyncClient;
    List<Baby> babyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        //This is from the amplify doc to enable us to integrate into our app
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();


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
}
