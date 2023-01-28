package com.example.Class4Demo;

import android.content.Intent;
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

import com.example.Class4Demo.databinding.FragmentAboutBinding;
import com.example.Class4Demo.model.Model;
import com.google.firebase.auth.FirebaseUser;


public class AboutFragment extends Fragment {

    FragmentAboutBinding binding;

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

        FirebaseUser user = Model.instance().getAuth().getCurrentUser();

        if (user != null) {
            Model.instance().getStudentById(user.getUid(), data -> {
                binding.textView4.setText("Name: " + data.getName());
            });
            binding.signOutBtn.setOnClickListener(v -> {
                Model.instance().getAuth().signOut();

                Intent i = new Intent(MyApplication.getMyContext(), WelcomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();
            });
        }
        return binding.getRoot();

    }
}