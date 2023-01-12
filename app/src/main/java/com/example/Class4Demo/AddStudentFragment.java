package com.example.Class4Demo;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import androidx.navigation.Navigation;

import com.example.Class4Demo.databinding.FragmentAddStudentBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Student;


public class AddStudentFragment extends Fragment {

    int d = 1, m = 0, y = 2023;
    FragmentAddStudentBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryAppLauncher;
    Boolean isAvatarSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.addStudentFragment);
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

        binding.dateInputEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Dialog dialog = new DatePickerDialog(getContext(), (datePicker, yy, mm, dd) -> {
                        y = yy;
                        m = mm;
                        d = dd;
                        binding.dateInputEt.setText("" + d + "/" + (m + 1) + "/" + y);
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
            String name = binding.nameEt.getText().toString();
            String stId = binding.idEt.getText().toString();
            String bDay = "" + d + "/" + (m + 1) + "/" + y;
            Student st = new Student(stId, name, bDay, "", "", false);
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

        binding.cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).
                popBackStack(R.id.studentsListFragment, false));

        binding.cameraButton.setOnClickListener(v -> {
            cameraLauncher.launch(null);
        });
        binding.galleryButton.setOnClickListener(v -> {
            galleryAppLauncher.launch("image/*");
        });

        return view;
    }


}
