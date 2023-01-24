package com.example.Class4Demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.Class4Demo.databinding.FragmentWelcomeBinding;

public class WelcomeFragment extends Fragment {

    FragmentWelcomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
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
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.signInBtn.setOnClickListener(v -> {
            NavDirections action = WelcomeFragmentDirections.actionWelcomeFragmentToSignInFragment();
            Navigation.findNavController(v).navigate(action);
        });

        binding.signUpBtn.setOnClickListener(v -> {
            NavDirections action = WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment();
            Navigation.findNavController(v).navigate(action);
        });

        return view;

    }
}