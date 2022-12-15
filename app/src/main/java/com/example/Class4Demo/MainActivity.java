package com.example.Class4Demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    BlueFragment frag1;
    BlueFragment frag2;
    BlueFragment frag3;
    BlueFragment frag4;
    BlueFragment inDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frag1 = BlueFragment.newInstance("1");
        frag2 = BlueFragment.newInstance("2");
        frag3 = BlueFragment.newInstance("3");
        frag4 = BlueFragment.newInstance("4");

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

    private void displayFragment(BlueFragment frag) {
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