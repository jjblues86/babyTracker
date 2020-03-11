package com.example.babytracker;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.babytracker.BabyFragment.OnListFragmentInteractionListener;
import java.util.List;


public class MyBabyRecyclerViewAdapter extends RecyclerView.Adapter<MyBabyRecyclerViewAdapter.MyBabyViewHolder> {

    private final List<Baby> babyList;
    private final OnListFragmentInteractionListener mListener;
    private final String TAG = "jj.ViewAdapter";

    public MyBabyRecyclerViewAdapter(List<Baby> babyList, OnListFragmentInteractionListener listener) {
        this.babyList = babyList;
        mListener = listener;
    }

    public static class MyBabyViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public Baby baby;
        public final TextView babyNameView;
        public final TextView babyDateOfBirthView;

        public MyBabyViewHolder(View view) {
            super(view);
            this.view = view;
            babyNameView = (TextView) view.findViewById(R.id.name);
            babyDateOfBirthView = (TextView) view.findViewById(R.id.dateOfBirth);
//            this.babyNameView = babyNameView;
//            this.babyDateOfBirthView = babyDateOfBirthView;
        }
    }

    //It creates a new row to be added to the table
    @NonNull
    @Override
    public MyBabyRecyclerViewAdapter.MyBabyViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_baby, parent, false);

        TextView taskTitle = parent.textViewTitle;
        taskTitle.setText(dataSet.get(RVIndex).getName());

        //this gives me access to the view
        final MyBabyViewHolder viewHolder = new MyBabyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickOnBabyCallback(viewHolder.baby);
            }
        });

        parent.itemView.setOnClickListener((event) -> {

            Context i = event.getContext();
            String potatoTitle = taskTitle.getText().toString();
            Intent intentionalToDetails = new Intent(i, BabyDetails.class);
            intentionalToDetails.putExtra("taskName", potatoTitle);
            i.startActivity(intentionalToDetails);
        });
        return viewHolder;
    }

//    @SuppressLint("SetTextI18n")
//    @Override
//    public void onBindViewHolder(final TaskHolder holder, final int RVIndex) {
//
//        TextView taskTitle = holder.textViewTitle;
//        taskTitle.setText(dataSet.get(RVIndex).getName());
//
//        TextView taskState = holder.textViewPriority;
//        taskState.setText("Priority: " + dataSet.get(RVIndex).getPriority());
//
//        // Credit: The illustrious TA James assisted me here
//        holder.itemView.setOnClickListener((event) -> {
//
//            Context context = event.getContext();
//            String potatoTitle = taskTitle.getText().toString();
//            Intent intentionalToDetails = new Intent(context, TaskDetail.class);
//            intentionalToDetails.putExtra("taskName", potatoTitle);
//            context.startActivity(intentionalToDetails);
//        });
//    }

    @Override
    public void onBindViewHolder(@NonNull MyBabyViewHolder holder, int position) {
        holder.babyNameView.setText(babyList.get(position).name);
        holder.babyDateOfBirthView.setText(babyList.get(position).dateOfBirth);
        holder.baby = babyList.get(position);
    }

    //Given the holder and the position index, fill in that view with right data for that position
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.babyNameView = mValues.get(position);
//        holder.mNameView.setText(mValues.get(position).getName());
////        holder.mDateOfBirthView.setText("" + mValues.get(position).getDateOfBirth());
////        holder.mImmunizationView.(mValues.get(position).getImmunization());
//
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "it was clicked");
////                if (null != mListener) {
////                    // Notify the active callbacks interface (the activity, if the
////                    // fragment is attached to one) that an item has been selected.
////                    mListener.onListFragmentInteraction(holder.mItem);
////                }
//            }
//        });
//    }

    @Override
    public int getItemCount() {

        return babyList.size();
    }

    public static interface OnListFragmentInteractionListener{
        public void onClickOnBabyCallback(Baby baby);
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView mNameView;
//        public final TextView mDateOfBirthView;
////        public final TextView mImmunizationView;
//        public Baby mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            mNameView = (TextView) view.findViewById(R.id.name);
//            mDateOfBirthView = (TextView) view.findViewById(R.id.dateOfBirth);
////            mImmunizationView = (TextView) view.findViewById(R.id.immunization);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mDateOfBirthView.getText() + "'";
//        }
//    }
}
