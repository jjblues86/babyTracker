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
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
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
        //
        ImageView taskImage = findViewById(R.id.baby_Image);
//        Picasso.get().load("https://bucketfortasks123331-taskenv.s3-us-west-2.amazonaws.com/public/public/c803c223-3ea7-46ca-ba0e-1dfc9f14de7d").into(taskImage);


        Intent showTaskID = getIntent();
        String showTaskName = showTaskID.getStringExtra("taskName");
        TextView textView1 = findViewById(R.id.baby_name);
        textView1.setText(showTaskName);
//        getTasksFromAmplify();
//        dataSet.get((int) dbTasks.taskDao().getSpecificViaTaskName(showTaskName).getId());
//        TaskData taskDataViaTaskName = dbTasks.taskDao().getSpecificViaTaskName(showTaskName);
//        Log.i("daylongTheGreat", String.valueOf(taskDataViaTaskName));
//
//        String showTaskStatus = taskDataViaTaskName.getPriority();
//        TextView textView2 = findViewById(R.id.taskDetail_State);
//        textView2.setText("Priority: " + showTaskStatus);
//
//        String showTaskDescription = taskDataViaTaskName.getDescription();
//        TextView textView3 = findViewById(R.id.taskDetail_Description);
//        textView3.setText(showTaskDescription);
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
                                                    switch (result.getUserState()){
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
    //
//    public void getTasksFromAmplify(){
//        awsSyncer.query(ListTodosQuery.builder().build())
//                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK).enqueue(getTasksCallBack);
//    }
    // Credit: https://frontrowviews.com/Home/Event/Play/5e1fa720eee6db204c80779e#
//    private GraphQLCall.Callback<ListTodosQuery.Data> getTasksCallBack = new GraphQLCall.Callback<ListTodosQuery.Data>() {
//        @Override
//        public void onResponse(@Nonnull Response<ListTodosQuery.Data> response) {
//            Log.i("daylongTheGreat", response.data().listTodos().items().toString());
//            if(dataSet.size() == 0 || response.data().listTodos().items().size() != dataSet.size()){
//                dataSet.clear();
//                for(ListTodosQuery.Item item : response.data().listTodos().items()){
//                    TaskData a = new TaskData(item.name(), item.priority(), item.description());
//                    dataSet.add(a);
//                }
//                Handler handlerForMainThread = new Handler(Looper.getMainLooper()){
//                    @Override
//                    public void handleMessage(Message inputMessage){
//                        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
//                        recyclerView.getAdapter().notifyDataSetChanged();
//                    }
//                };
//                handlerForMainThread.obtainMessage().sendToTarget();
//            }
//        }
//        @Override
//        public void onFailure(@Nonnull ApolloException e) {
//            Log.e("daylongTheGreat", e.toString());
//        }
//    };
    //
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
            Toast.makeText(BabyDetails.this, "Logging Out User", Toast.LENGTH_LONG).show();
            AWSMobileClient.getInstance().signOut();
            finish();
        }
        return(super.onOptionsItemSelected(item));
    }
}