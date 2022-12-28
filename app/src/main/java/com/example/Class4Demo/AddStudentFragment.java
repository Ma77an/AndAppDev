package com.example.Class4Demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;


public class AddStudentFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.addStudentFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, this, Lifecycle.State.RESUMED);
    }


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

        cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).
                popBackStack(R.id.studentsListFragment, false));
        return view;
    }


}
