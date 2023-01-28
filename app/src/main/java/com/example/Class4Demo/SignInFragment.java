package com.example.Class4Demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import com.example.Class4Demo.databinding.FragmentSignInBinding;
import com.example.Class4Demo.model.Model;


public class SignInFragment extends Fragment {

    FragmentSignInBinding binding;
    String uid;

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

        binding = FragmentSignInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.signInBtn.setOnClickListener(v -> {
            String email = binding.emailTextField.getEditText().getText().toString();
            String password = binding.passwordTextField.getEditText().getText().toString();
            Log.d("TAG", "onCreateView: THE EMAIL AND PASSWORD|::::" + email + password);

            if (password.length() < 6) {
                Toast.makeText(MyApplication.getMyContext(), "Password should be 6 characters min",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (email.length() < 1) {
                Toast.makeText(MyApplication.getMyContext(), "Please fill email to log in",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            Model.instance().signIn(email, password, success -> {
                if (!success) {
                    Toast.makeText(MyApplication.getMyContext(), "Login Failed, Try Again!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApplication.getMyContext(), "Log In Succeeded!!!",
                            Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(MyApplication.getMyContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    getActivity().finish();
                }
            });
        });

        binding.cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).
                popBackStack(R.id.welcomeFragment, false));


        return view;
    }
}