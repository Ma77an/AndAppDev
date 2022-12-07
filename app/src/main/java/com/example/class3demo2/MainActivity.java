package com.example.class3demo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.main_add_student_btn);

        blueFrag = BlueFragment.newInstance("New Title");


        btn.setOnClickListener(view -> {

//            Intent intent = new Intent(this,AddStudentActivity.class);
//            startActivity(intent);

            if (blueInDisplay){
                removeBlueFragment();
            } else {
                showBlueFragment();
            }
        });

    }
    boolean blueInDisplay = false;
    BlueFragment blueFrag = null;

    void showBlueFragment(){
        if(blueFrag == null){
            blueFrag = new BlueFragment();
        }
        blueInDisplay = true;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.add(R.id.main_fragment_container, blueFrag);
        tran.addToBackStack("SaritHadad");
        tran.commit();
    }

    void removeBlueFragment(){
        blueInDisplay = false;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.remove(blueFrag);
        tran.commit();
    }
}