package com.example.Class4Demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class AddStudentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);
        EditText nameEt = view.findViewById(R.id.addstudent_name_et);
        EditText idEt = view.findViewById(R.id.addstudent_id_et);
        TextView messageTv = view.findViewById(R.id.addstudent_message);
        Button saveBtn = view.findViewById(R.id.addstudent_save_btn);
        Button cancelBtn = view.findViewById(R.id.addstudent_cancell_btn);

        saveBtn.setOnClickListener(view1 -> {
            String name = nameEt.getText().toString();
            messageTv.setText(name);
        });

        //cancelBtn.setOnClickListener(view -> finish());
        return view;
    }

}