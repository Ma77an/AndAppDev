package com.example.Class4Demo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.Class4Demo.databinding.FragmentEditStudentBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Student;
import com.squareup.picasso.Picasso;

public class EditStudentFragment extends Fragment {

    FragmentEditStudentBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryAppLauncher;
    Boolean isAvatarSelected = false;
    int d, m, y;
    String uid;
    String photoURL;

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
            String[] date = st.getBirthday().split("/");
            d = Integer.parseInt(date[0]);
            m = Integer.parseInt((date[1])) - 1;
            y = Integer.parseInt(date[2]);
            photoURL = st.getAvatar();
            studentDetails(st);
            binding.progressBar.setVisibility(View.GONE);
        });

        binding.bithdayInput.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Dialog dialog = new DatePickerDialog(getContext(), (datePicker, yy, mm, dd) -> {
                        y = yy;
                        m = mm;
                        d = dd;
                        binding.bithdayInput.setText("" + d + "/" + (m + 1) + "/" + y);
                    },
                            y, m, d);
                    dialog.show();
                    return true;
                }
                return false;
            }
        });

        binding.saveBtn.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            String stId = binding.idEt.getText().toString();
            String name = binding.nameEt.getText().toString();
            String phone = binding.phoneEt.getText().toString();
            String instagram = binding.instagramEt.getText().toString();
            String bDay = "" + d + "/" + (m + 1) + "/" + y;
            Student st = new Student(stId, name, photoURL, bDay, phone, instagram);
            if (isAvatarSelected) {
                binding.avatarImg.setDrawingCacheEnabled(true);
                binding.avatarImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(stId, bitmap, url -> {
                    if (url != null) {
                        st.setAvatar(url);
                    }
                    Model.instance().addStudent(st, (unused) -> {
                        Navigation.findNavController(view1).popBackStack();
                        binding.progressBar.setVisibility(View.GONE);
                    });
                });
            } else {
                Model.instance().addStudent(st, (unused) -> {
                    Navigation.findNavController(view1).popBackStack();
                    binding.progressBar.setVisibility(View.GONE);
                });
            }
        });

        binding.cameraButton.setOnClickListener(v -> {
            cameraLauncher.launch(null);
        });
        binding.galleryButton.setOnClickListener(v -> {
            galleryAppLauncher.launch("image/*");
        });


        return view;
    }

    public void studentDetails(Student st) {
        binding.idEt.setText(st.getId());
        binding.nameEt.setText(st.getName());
        binding.phoneEt.setText(st.getPhone());
        binding.instagramEt.setText(st.getInstagram());
        binding.bithdayInput.setText(st.getBirthday());
        if (!st.getAvatar().equals("")) {
            Picasso.get().load(st.getAvatar()).placeholder(R.drawable.avatar).into(binding.avatarImg);
        } else {
            binding.avatarImg.setImageResource(R.drawable.avatar);
        }
    }
}