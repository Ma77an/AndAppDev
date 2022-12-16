package com.example.Class4Demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    StudentsListFragment frag1;
    BlueFragment frag2;
    BlueFragment frag3;
    StudentsListFragment frag4;
    Fragment inDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frag1 = new StudentsListFragment();
        frag2 = BlueFragment.newInstance("2");
        frag3 = BlueFragment.newInstance("3");
        frag4 = new StudentsListFragment();

        Button btn1 = findViewById(R.id.main_btn_1);
        Button btn2 = findViewById(R.id.main_btn_2);
        Button btn3 = findViewById(R.id.main_btn_3);
        Button btn4 = findViewById(R.id.main_btn_4);

        btn1.setOnClickListener(view -> {
            displayFragment(frag1);
        });
        btn2.setOnClickListener(view -> {
            displayFragment(frag2);
        });
        btn3.setOnClickListener(view -> {
            displayFragment(frag3);
        });
        btn4.setOnClickListener(view -> {
            displayFragment(frag4);
        });
        displayFragment(frag1);
    }

    private void displayFragment(Fragment frag) {
        if (inDisplay == frag){
            return;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.add(R.id.main_fragment_container, frag);
        if (inDisplay != null) {
            tran.remove(inDisplay);
        }
        tran.addToBackStack("SaritHadad");
        tran.commit();
        inDisplay = frag;
    }

}