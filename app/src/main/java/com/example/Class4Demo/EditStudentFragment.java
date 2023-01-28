package com.example.Class4Demo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Class4Demo.databinding.FragmentEditStudentBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Student;
import com.squareup.picasso.Picasso;

public class EditStudentFragment extends Fragment {

    FragmentEditStudentBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryAppLauncher;
    Boolean isAvatarSelected = false;
    String uid;

    public EditStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = getArguments().getString("uid");

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
                new ActivityResultCallback<Bitmap>() {
                    @Override
                    public void onActivityResult(Bitmap result) {
                        if (result != null) {
                            binding.avatarImg.setImageBitmap(result);
                            isAvatarSelected = true;
                        }
                    }
                });

        galleryAppLauncher = registerForActivityResult(new
                ActivityResultContracts.GetContent(), new
                ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        Log.d("TAG", "onActivityResult " + result.toString());
                        binding.avatarImg.setImageURI(result);
                        isAvatarSelected = true;
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditStudentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Model.instance().getStudentById(uid, st -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            studentDetails(st);
            binding.progressBar.setVisibility(View.GONE);
        });


        return view;
    }

    public void studentDetails(Student st) {
        binding.nameEt.setText(st.getName());
        binding.phoneEt.setText(st.getPhone());
        binding.instagramEt.setText("@" + st.getInstagram());
        binding.bithdayInput.setText(st.getBirthday());
        if (!st.getAvatar().equals("")) {
            Picasso.get().load(st.getAvatar()).placeholder(R.drawable.avatar).into(binding.avatarImg);
        } else {
            binding.avatarImg.setImageResource(R.drawable.avatar);
        }
    }
}