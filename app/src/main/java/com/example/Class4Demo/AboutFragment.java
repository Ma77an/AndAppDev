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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.example.Class4Demo.databinding.FragmentAboutBinding;


public class AboutFragment extends Fragment {

    FragmentAboutBinding binding;

    int d = 1, m = 0, y = 2023;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.aboutFragment);
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
//        return inflater.inflate(R.layout.fragment_about, container, false);
        binding = FragmentAboutBinding.inflate(inflater, container, false);

        binding.dateEditBtn.setOnClickListener(view -> {
            Dialog dialog = new DatePickerDialog(getContext(), (datePicker, yy, mm, dd) -> {
                y = yy;
                m = mm;
                d = dd;
                setDate();
            },
                    y, m, d);
            dialog.show();
        });

        binding.dateInputEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Dialog dialog = new DatePickerDialog(getContext(), (datePicker, yy, mm, dd) -> {
                        y = yy;
                        m = mm;
                        d = dd;
                        setDate2();
                    },
                            y, m, d);
                    dialog.show();
                    return true;
                }
                return false;
            }
        });
        return binding.getRoot();

    }

    void setDate() {
        binding.dateTv.setText("" + d + "/" + (m + 1) + "/" + y);
    }

    void setDate2() {
        binding.dateInputEt.setText("" + d + "/" + (m + 1) + "/" + y);
    }
}