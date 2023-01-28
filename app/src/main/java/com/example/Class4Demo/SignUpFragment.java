package com.example.Class4Demo;

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

import com.example.Class4Demo.databinding.FragmentSignUpBinding;
import com.example.Class4Demo.model.Model;


public class SignUpFragment extends Fragment {

    FragmentSignUpBinding binding;
    String uid;

    public static SignUpFragment newInstance(String uid) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle data = new Bundle();
        data.putString("uid", uid);
        fragment.setArguments(data);
        return fragment;
    }

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

        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.registerBtn.setOnClickListener(v -> {
            String email = binding.emailTextField.getEditText().getText().toString();
            String password = binding.passwordTextField.getEditText().getText().toString();
            Log.d("TAG", "onCreateView: THE EMAIL AND PASSWORD|::::" + email + password);

            if (password.length() < 6) {
                Toast.makeText(MyApplication.getMyContext(), "Password should be 6 characters min",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            Model.instance().signUp(email, password, data -> {
                if (data.equals("")) {
                    return;
                }

                SignUpFragmentDirections.ActionSignUpFragmentToAddStudentFragment action
                        = SignUpFragmentDirections.actionSignUpFragmentToAddStudentFragment(data);
                Navigation.findNavController(view).navigate(action);
            });
        });

        binding.cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).
                popBackStack(R.id.welcomeFragment, false));


        return view;
    }
}