package com.example.Class4Demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Results;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.logging.type.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    String API = "d154e01470f2e0a318ad97ac7b413442";
    NavController navController;
//    LocationManager lc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//    Criteria criteria = new Criteria();
//    String locality;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Model.instance().refreshAllPosts();
        Model.instance().refreshAllStudents();

        FirebaseUser user = Model.instance().getAuth().getCurrentUser();
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        View addBtn = findViewById(R.id.floatingActionButton2);
        addBtn.setOnClickListener(v -> {
            navController.navigate(R.id.addPostFragment);
        });

        findViewById(R.id.weather);

        bottomNavigationView.setOnItemReselectedListener(item -> {
            navController.popBackStack(item.getItemId(), false);
        });
        getWeatherData("tel aviv");
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

    private void getWeatherData(String name){

        Api apiInterface = RetrofitClient.getInstance().getMyApi();

        Call<Results> call = apiInterface.getWeatherData(name);

        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                String ll = response.body().getWeather().get(0).getDescription();
                Log.d("TAG", "onItemClick: " + ll);


            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

            }
        });

    }









}
