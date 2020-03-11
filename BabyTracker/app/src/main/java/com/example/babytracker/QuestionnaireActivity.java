package com.example.babytracker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.CreateBabyMutation;
import com.amazonaws.amplify.generated.graphql.ListBabysQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import type.CreateBabyInput;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;


public class QuestionnaireActivity extends AppCompatActivity {
    private String imageUrl = "http://via.placeholder.com/150";

    String TAG = "MainActivity";
    List<Baby> babyList;
    AWSAppSyncClient mAWSAppSyncClient;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));

        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        Button imgBtn = findViewById(R.id.uploadImage);

        Button submitBtn = findViewById(R.id.submit);


        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chooseFile = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(chooseFile, 42);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText name = findViewById(R.id.newBabyNameActual);
                String nameText = name.getText().toString();

                EditText dateOfBirth = findViewById(R.id.newBabyDOBActual);
                String dateOfBirthText = dateOfBirth.getText().toString();

                runMutation(nameText, dateOfBirthText);

                Button submit = findViewById(R.id.submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context showConfirmation = getApplicationContext();
                        CharSequence confirmationText = "Submitted";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(showConfirmation, confirmationText, duration);
                        toast.show();
                        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 350, 350);
                    }
                });
            }
        });
    }
    ///  end of create


    private GraphQLCall.Callback<CreateBabyMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateBabyMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateBabyMutation.Data> response) {
            Log.i("voytov", "Added baby");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };

    public void runMutation(String name, String dob) {

        CreateBabyInput createBabyInput = CreateBabyInput.builder()
                .name(name)
                .dob(dob)
                .imageUrl(imageUrl)
                .build();

        mAWSAppSyncClient.mutate(CreateBabyMutation.builder().input(createBabyInput).build())
                .enqueue(mutationCallback);
    }

    public void uploadWithTransferUtility(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        // String picturePath contains the path of selected Image
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();

        final String uuid = UUID.randomUUID().toString();
        final TransferObserver uploadObserver =
                transferUtility.upload(
                        "public/" + uuid,
                        new File(picturePath), CannedAccessControlList.PublicRead);


        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    Log.i(TAG, "successfully uploaded");
                    imageUrl = "https://" + uploadObserver.getBucket() + uploadObserver.getKey();

                    Log.i(TAG, "path to the s3 image please" + "https://" + uploadObserver.getBucket() + "/public" + uuid);


                    Log.i(TAG, "ACTUAL stuff" + uploadObserver.getBucket() + uploadObserver.getKey());

                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int) percentDonef;
                Log.i(TAG, "uploading to bucket :" + uploadObserver.getBucket());
                Log.i(TAG, "uploading with key :" + uploadObserver.getKey());
                Log.d("voytov", "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                ex.printStackTrace();
                Log.i(TAG, "error");
            }

        });

        Log.d("voytov", "Bytes Transferred: " + uploadObserver.getBytesTransferred());
        Log.d("voytov", "Bytes Total: " + uploadObserver.getBytesTotal());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        Log.d(TAG, "on activity uri   : " + resultData.getData());
        Log.d(TAG, "on getpath: " + getPath(resultData.getData()));

        if (resultData != null) {

            getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));
            // Initialize the AWSMobileClient if not initialized
            AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
//                    Log.i(TAG, "AWSMobileClient initialized. User State is " + userStateDetails.getUserState());

                    Log.i(TAG, "URI " + resultData.getData());

                    uploadWithTransferUtility(resultData.getData());


                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, "Initialization error.", e);
                }
            });
        }
    }

    public String getPath(Uri uri) {

        String path = null;
        String[] projection = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor == null) {
            path = uri.getPath();
        } else {
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }

        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);
    }

    @Override
    public void onResume() {
        super.onResume();

        /////////////////sharing image to the app from gallery
        // Get the intent that started this activity
        Intent intent = getIntent();

        // Figure out what to do based on the intent type
        String typeActivity = intent.getType();

        Log.i(TAG, "intent POS" + intent.toString());
        if (typeActivity != null && typeActivity.contains("image/")) {

            Uri imageUrl = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            Log.i(TAG, "image from external share uri: " + imageUrl);
            Log.i(TAG, "maybe a path? " + getPath(imageUrl));
//            uploadWithTransferUtility(imageUrl);

        }
    }
}
