package com.example.Class4Demo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.registerBtn.setOnClickListener(v -> {
            String email = binding.emailTextField.getEditText().getText().toString();
            String password = binding.passwordTextField.getEditText().getText().toString();
            Log.d("TAG", "onCreateView: THE EMAIL AND PASSWORD|::::" + email + password);


            Model.instance().createStudent(email, password, data -> {
//                uid = data;
                SignUpFragmentDirections.ActionSignUpFragmentToAddStudentFragment action
                        = SignUpFragmentDirections.actionSignUpFragmentToAddStudentFragment(data);
                Navigation.findNavController(view).navigate(action);
            });

//            Student st = new Student(uid, "", "", "", "", "", false);
//            Model.instance().addStudent(st, data -> {
//                SignUpFragmentDirections.ActionSignUpFragmentToAddStudentFragment action
//                        = SignUpFragmentDirections.actionSignUpFragmentToAddStudentFragment(st.getId());
//                Navigation.findNavController(view).navigate(action);
//            });
        });


        return view;
    }
}