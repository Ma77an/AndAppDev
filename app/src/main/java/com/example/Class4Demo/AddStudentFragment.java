package com.example.Class4Demo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.example.Class4Demo.databinding.FragmentAddStudentBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Student;


public class AddStudentFragment extends Fragment {

    int d = 1, m = 0, y = 2023;
    FragmentAddStudentBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryAppLauncher;
    Boolean isAvatarSelected = false;
    String uid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (data != null) {
            uid = data.getString("uid");
            Log.d("TAG", "onCreate: !@#@#$@!#%@!#$!±@$!±$!±@$");
            Log.d("TAG", "onCreate: !@#@#$@!#%@!#$!±@$!±$!±@$" + uid);

        }

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
//                menu.removeItem(R.id.addPostFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, this, Lifecycle.State.RESUMED);

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
        // Inflate the layout for this fragment
        binding = FragmentAddStudentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.idEt.setText(uid);
        Log.d("TAG", "onCreateView: uid is" + uid);

        binding.bithdayInput.setOnTouchListener(new View.OnTouchListener() {
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
            Student st = new Student(stId, name, "", bDay, phone, instagram);
            if (isAvatarSelected) {
                binding.avatarImg.setDrawingCacheEnabled(true);
                binding.avatarImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(stId, bitmap, url -> {
                    if (url != null) {
                        st.setAvatar(url);
                    }
                    Model.instance().addStudent(st, (unused) -> {
//                        Navigation.findNavController(view1).popBackStack();
                        binding.progressBar.setVisibility(View.GONE);
                    });
                });
            } else {
                Model.instance().addStudent(st, (unused) -> {
//                    Navigation.findNavController(view1).popBackStack();
                    binding.progressBar.setVisibility(View.GONE);
                });
            }

//            new Handler().post(new Runnable() {
//
//                @Override
//                public void run() {
//                    Intent intent = getActivity().getIntent();
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
//                            | Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    getActivity().overridePendingTransition(0, 0);
//                    getActivity().finish();
//
//                    getActivity().overridePendingTransition(0, 0);
//                    startActivity(intent);
//                }
//            });
            Intent i = new Intent(MyApplication.getMyContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            getActivity().finish();
        });

//        binding.cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).
//                popBackStack(R.id.studentsListFragment, false));

        binding.cameraButton.setOnClickListener(v -> {
            cameraLauncher.launch(null);
        });
        binding.galleryButton.setOnClickListener(v -> {
            galleryAppLauncher.launch("image/*");
        });

        return view;
    }


}
