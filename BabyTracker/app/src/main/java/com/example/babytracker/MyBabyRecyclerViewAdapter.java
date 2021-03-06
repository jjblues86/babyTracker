package com.example.babytracker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.babytracker.BabyFragment.OnListFragmentInteractionListener;
import com.example.babytracker.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
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
        }
    }

    //It creates a new row to be added to the table
    @NonNull
    @Override
    public MyBabyRecyclerViewAdapter.MyBabyViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_baby, parent, false);

        //this gives me access to the view
        final MyBabyViewHolder viewHolder = new MyBabyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Log.i("voytov", viewHolder.baby.name);
                mListener.onClickOnBabyCallback(viewHolder.baby);
            }
        });
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyBabyViewHolder holder, int position) {
        holder.babyNameView.setText(babyList.get(position).name);
        holder.babyDateOfBirthView.setText(babyList.get(position).dateOfBirth);
        holder.babyDateOfBirthView.setText("" + babyList.get(position).dateOfBirth);
        holder.baby = babyList.get(position);
        Log.i("voytov", position +"");

        Log.i("voytov", holder.baby.toString());
    }
    @Override
    public int getItemCount() {

        return babyList.size();
    }

    public static interface OnListFragmentInteractionListener{
        public void onClickOnBabyCallback(Baby baby);
    }
}
