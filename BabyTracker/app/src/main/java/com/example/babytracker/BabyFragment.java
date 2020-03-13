package com.example.babytracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.babytracker.dummy.DummyContent;
import com.example.babytracker.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BabyFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_PARAM1 = "babyName";
    private static final String ARG_PARAM2 = "babyDateOfBirth";

    // TODO: Customize parameters
    private String mBabyName;
    private String mBabyDateOfBirth;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BabyFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BabyFragment newInstance(String babyName, String babyDateOfBirth) {
        BabyFragment fragment = new BabyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, babyName);
        args.putString(ARG_PARAM2, babyDateOfBirth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mBabyName = getArguments().getString(ARG_PARAM1);
            mBabyDateOfBirth = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_baby_list, container, false);

        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            List<Baby> listOfBabies = new LinkedList<>();
//            listOfBabies.add(new Baby("Martin", 01/01/01, true));
//            listOfBabies.add(new Baby("Sergey", 9/20/90, true));
//            listOfBabies.add(new Baby("Dayne", 5/11/81, false));
//            listOfBabies.add(new Baby("Romell", 4/01/66, false));
//            listOfBabies.add(new Baby("Jerome", 01/03/30, true));
//
//            recyclerView.setAdapter(new MyBabyRecyclerViewAdapter(listOfBabies, mListener));
//        }
//        return view;
    }

    public void whenButtonIsPressed(Uri uri){
        if(mListener != null){
            mListener.onListFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {

//            mListener = (OnListFragmentInteractionListener) context;


        }
//        else
//            {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Uri uri);
    }
}
