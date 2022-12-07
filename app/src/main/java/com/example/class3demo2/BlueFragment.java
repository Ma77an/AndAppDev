package com.example.class3demo2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BlueFragment extends Fragment {


    public BlueFragment() {
        // Required empty public constructor
    }

    TextView myTitleTv;
    String myTitle;

    public static BlueFragment newInstance(String title) {
        BlueFragment fragment = new BlueFragment();
        Bundle data = new Bundle();
        data.putString("Title", title);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (data != null){
            myTitle = data.getString("Title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_blue, container, false);
        myTitleTv = view.findViewById(R.id.bluefrag_title_tv);
        if (myTitle != null){
            myTitleTv.setText(myTitle);
        }
        return view;
    }
}