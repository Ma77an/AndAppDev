package com.example.Class4Demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.Class4Demo.databinding.FragmentBlueBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Student;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class BlueFragment extends Fragment {


    public BlueFragment() {
        // Required empty public constructor
    }

    FragmentBlueBinding binding;
    String myTitle;
    String id;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (data != null) {
            id = data.getString("studentId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBlueBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if (Objects.equals(id, Model.instance().getAuth().getUid())) {
            binding.detailsEditBtn.setVisibility(View.VISIBLE);
        }

        Model.instance().getStudentById(id, st1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            studentDetails(st1);
            binding.progressBar.setVisibility(View.GONE);
        });

        binding.detailsEditBtn.setOnClickListener(v -> {
            BlueFragmentDirections.ActionBlueFragmentToEditStudentFragment action
                    = BlueFragmentDirections.actionBlueFragmentToEditStudentFragment(id);
            Navigation.findNavController(v).navigate(action);
        });

        return view;
    }

    public void studentDetails(Student st) {
        binding.nameTv2.setText(st.getName());
        binding.phoneTv2.setText(st.getPhone());
        binding.instagramTv2.setText("@" + st.getInstagram());
        binding.bDayTv2.setText(st.getBirthday());
        if (!st.getAvatar().equals("")) {
            Picasso.get().load(st.getAvatar()).placeholder(R.drawable.avatar).into(binding.avatarImg);
        } else {
            binding.avatarImg.setImageResource(R.drawable.avatar);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }
}