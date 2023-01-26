package com.example.Class4Demo;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.example.Class4Demo.databinding.FragmentAddPostBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Post;
import com.google.firebase.Timestamp;

public class AddPostFragment extends Fragment {

    FragmentAddPostBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryAppLauncher;
    Boolean isImageSelected = false;
    String uid;
    boolean imagePost;

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
                            binding.postImg.setImageBitmap(result);
                            isImageSelected = true;
                        }
                    }
                });

        galleryAppLauncher = registerForActivityResult(new
                ActivityResultContracts.GetContent(), new
                ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        Log.d("TAG", "onActivityResult " + result.toString());
                        binding.postImg.setImageURI(result);
                        isImageSelected = true;
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                binding.postImg.setVisibility(View.GONE);
                binding.galleryButton.setVisibility(View.GONE);
                binding.cameraButton.setVisibility(View.GONE);
            } else {
                binding.postImg.setVisibility(View.VISIBLE);
                binding.galleryButton.setVisibility(View.VISIBLE);
                binding.cameraButton.setVisibility(View.VISIBLE);
            }
            imagePost = isChecked;
        });

        binding.saveBtn.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            String title = binding.titleEt.getText().toString();
            String desc = binding.descriptionEt.getText().toString();
            imagePost = binding.switch1.isChecked();
            uid = Model.instance().getAuth().getCurrentUser().getUid();
            String postId = uid + Timestamp.now().getSeconds();

            Post pst = new Post(postId, uid, title, desc, imagePost, "");


            if (imagePost && isImageSelected) {
                binding.postImg.setDrawingCacheEnabled(true);
                binding.postImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.postImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(postId, bitmap, url -> {
                    if (url != null) {
                        pst.setPhotoUrl(url);
                    }
                    Model.instance().addPost(pst, (unused) -> {
                        Navigation.findNavController(view1).popBackStack();
                        binding.progressBar.setVisibility(View.GONE);
                    });
                });
            } else {
                Model.instance().addPost(pst, (unused) -> {
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
}