package com.example.Class4Demo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import com.example.Class4Demo.databinding.FragmentAddStudentBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Student;


public class AddStudentFragment extends Fragment {

    FragmentAddStudentBinding binding;
    int d = 1, m = 0, y = 2023;

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
        binding = FragmentAddStudentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Button saveBtn = view.findViewById(R.id.save_btn);
        Button cancelBtn = view.findViewById(R.id.cancel_btn);

        binding.dateInputEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Dialog dialog = new DatePickerDialog(getContext(), (datePicker, yy, mm, dd) -> {
                        y = yy;
                        m = mm;
                        d = dd;
                        binding.dateInputEt.setText("" + d + "/" + (m + 1) + "/" + y);
                    },
                            y, m, d);
                    dialog.show();
                    return true;
                }
                return false;
            }
        });

        saveBtn.setOnClickListener(view1 -> {
            String name = binding.nameEt.getText().toString();
            String stId = binding.idEt.getText().toString();
            String bDay = "" + d + "/" + (m + 1) + "/" + y;
            Student st = new Student(stId, name, bDay, "", "", false);
            Model.instance().addStudent(st, () -> {
                Navigation.findNavController(view1).popBackStack();

            });
        });

        cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).
                popBackStack(R.id.studentsListFragment, false));
        return view;
    }


}
