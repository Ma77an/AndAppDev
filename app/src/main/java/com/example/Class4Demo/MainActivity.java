package com.example.Class4Demo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Results;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Weather weather;
    String weatherInfo;
    NavController navController;
    ActionBar actionBar;
    BottomNavigationView bottomNavigationView;

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Model.instance().refreshAllStudents();


        FirebaseUser user = Model.instance().getAuth().getCurrentUser();
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);

        bottomNavigationView = findViewById(R.id.main_bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        View addBtn = findViewById(R.id.floatingActionButton2);
        addBtn.setOnClickListener(v -> {
            navController.navigate(R.id.addPostFragment);
        });

        bottomNavigationView.setOnItemReselectedListener(item -> {
            navController.popBackStack(item.getItemId(), false);
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
                    navController.navigate(item.getItemId());
                    return true;
                }
        );

    }

    MenuItem icon;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        icon = menu.findItem(R.id.weather_icon);
        getWeatherData("Rishon LeZion", menu.findItem(R.id.weather_info));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.weather_info) {
            Picasso.get().load(weather.getIconUrl())
                    .placeholder(R.drawable.twotone_waving_hand_24)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Log.d("onPrepareLoad", "icon loaded " + bitmap.toString());
                            icon.setIcon(new BitmapDrawable(getResources(), bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            Log.d("onPrepareLoad", "Loading failed" + e.toString());
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            Log.d("onPrepareLoad", "Loading your icon...");
//                                item.setIcon(placeHolderDrawable);
                        }
                    });
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            navController.popBackStack();
        } else {
            return NavigationUI.onNavDestinationSelected(item, navController);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getWeatherData(String name, MenuItem item) {

        executor.execute(() -> {
            WeatherApi weatherApiInterface = RetrofitClient.getInstance().getMyApi();

            Call<Results> call = weatherApiInterface.getWeatherData(name);

            call.enqueue(new Callback<Results>() {
                @Override
                public void onResponse(Call<Results> call, Response<Results> response) {
                    weatherInfo = response.body().getMain().getTemp() + "°C" + " - " +
                            response.body().getWeather().get(0).getDescription();
                    weather = response.body().getWeather().get(0);
                    item.setTitle(weatherInfo);
                    Log.d("TAG", "getWeather: " + response.body().getWeather().get(0).getIconUrl());
                }

                @Override
                public void onFailure(Call<Results> call, Throwable t) {
                }
            });
            mainHandler.post(() -> item.setTitle(weatherInfo));
        });
    }
}


//    private void getWeatherData(String name, MenuItem item) {
//
//        executor.execute(() -> {
//            WeatherApi weatherApiInterface = RetrofitClient.getInstance().getMyApi();
//
//            Call<Results> call = weatherApiInterface.getWeatherData(name);
//
//            call.enqueue(new Callback<Results>() {
//                @Override
//                public void onResponse(Call<Results> call, Response<Results> response) {
//                    weatherInfo = response.body().getMain().getTemp() + "°C" + " - " +
//                            response.body().getWeather().get(0).getDescription();
//                    weatherIcon = response.body().getWeather().get(0).getIconUrl();
//                    item.setTitle(weatherInfo);
//                    Log.d("TAG", "getWeather: " + response.body().getWeather().get(0).getIconUrl());
//                }
//
//                @Override
//                public void onFailure(Call<Results> call, Throwable t) {
//                }
//            });
//            mainHandler.post(() -> item.setTitle(weatherInfo));
//        });
//    }
// Picasso.get().load(response.body().getWeather().get(0).getIconUrl())
//         .placeholder(R.drawable.twotone_waving_hand_24)
//         .into(new Target() {
//@Override
//public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//        Log.d("onPrepareLoad", "icon loaded " + bitmap.toString());
//        item.setIcon(new BitmapDrawable(getResources(), bitmap));
//        }
//
//@Override
//public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//        Log.d("onPrepareLoad", "Loading failed" + e.toString());
//        }
//
//@Override
//public void onPrepareLoad(Drawable placeHolderDrawable) {
//        Log.d("onPrepareLoad", "Loading your icon...");
////                                item.setIcon(placeHolderDrawable);
//        }
//        });