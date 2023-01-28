package com.example.Class4Demo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.Class4Demo.model.Model;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = Model.instance().getAuth().getCurrentUser();
        Model.instance().refreshAllPosts();
        Model.instance().refreshAllStudents();

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);

        View addBtn = findViewById(R.id.floatingActionButton2);
        addBtn.setOnClickListener(v -> {
            navController.navigate(R.id.addPostFragment);
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.aboutFragment) {
//            new AlertDialogFragment().show(getSupportFragmentManager(), "TAG");
//            return true;}else

        if (item.getItemId() == android.R.id.home) {
            navController.popBackStack();
        } else {
            return NavigationUI.onNavDestinationSelected(item, navController);
        }
        return super.onOptionsItemSelected(item);
    }
}