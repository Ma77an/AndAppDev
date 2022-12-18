package com.example.Class4Demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class BlueFragment extends Fragment {


    public BlueFragment() {
        // Required empty public constructor
    }

    TextView myTitleTv;
    String myTitle;
    Button backBtn;

    public static BlueFragment newInstance(String title) {
        BlueFragment fragment = new BlueFragment();
        Bundle data = new Bundle();
        data.putString("TITLE", title);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (data != null) {
            myTitle = data.getString("TITLE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blue, container, false);

        myTitle = BlueFragmentArgs.fromBundle(getArguments()).getBlueTitle();

        myTitleTv = view.findViewById(R.id.bluefrag_title_tv);
        if (myTitle != null) {
            myTitleTv.setText(myTitle);
        }

        backBtn = view.findViewById(R.id.bluefrag_back_btn);
        backBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());

        return view;
    }
}