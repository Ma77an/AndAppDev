package com.example.Class4Demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Class4Demo.databinding.FragmentBlueBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Student;

public class BlueFragment extends Fragment {


    public BlueFragment() {
        // Required empty public constructor
    }

    FragmentBlueBinding binding;
    String myTitle;
    String id;
    Student st;

    public static BlueFragment newInstance(String title, String stId) {
        BlueFragment fragment = new BlueFragment();
        Bundle data = new Bundle();
        data.putString("TITLE", title);
        data.putString("ID", stId);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (data != null) {
            myTitle = data.getString("TITLE");
            id = data.getString("studentId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBlueBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Model.instance().getStudentById(id, st1 -> {
            studentDetails(st1);
        });

        return view;
    }

    public void studentDetails(Student st) {
        binding.nameTv.setText("Name: " + st.getName());
        binding.idTv.setText("ID: " + st.getId());
        binding.phoneTv.setText("Phone: " + st.getPhone());
        binding.addressTv.setText("Address: ");
        binding.bDayTv.setText("Birthday: " + st.getBDate());
        binding.cb.setChecked(st.isChecked());
    }


}