package com.example.Class4Demo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.Class4Demo.databinding.FragmentEditPostBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Post;
import com.squareup.picasso.Picasso;


public class EditPostFragment extends Fragment {

    FragmentEditPostBinding binding;

    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryAppLauncher;
    String postId;
    String photoURL = "";
    Boolean imagePost = false;
    Boolean isImageSelected = false;
    String URL;

    public EditPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postId = getArguments().getString("postId");

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
        binding = FragmentEditPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Model.instance().getPostById(postId, pst -> {
            binding.postProgressBar.setVisibility(View.VISIBLE);
            binding.titleEt.setText(pst.getTitle());
            imagePost = pst.getPhoto();
            photoURL = pst.getPhotoUrl();
            if (pst.getPhoto() && !pst.getPhotoUrl().equals("")) {
                binding.imagePostSwitch.setChecked(true);
                Picasso.get().load(pst.getPhotoUrl()).into(binding.postImg);
            } else {
                binding.imagePostSwitch.setChecked(false);
                binding.postImg.setVisibility(View.GONE);
                binding.galleryButton.setVisibility(View.GONE);
                binding.cameraButton.setVisibility(View.GONE);
            }
            binding.descriptionEt.setText(pst.getDesc());
            binding.postProgressBar.setVisibility(View.GONE);
        });

        binding.imagePostSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(MyApplication.getMyContext(), "This is a post " + (isChecked ? "with Image" : "without image"),
                    Toast.LENGTH_SHORT).show();
            if (!isChecked) {
                binding.postImg.setVisibility(View.GONE);
                binding.galleryButton.setVisibility(View.GONE);
                binding.cameraButton.setVisibility(View.GONE);
                imagePost = false;
            } else {
                binding.postImg.setVisibility(View.VISIBLE);
                if (!photoURL.equals("")) {
                    Picasso.get().load(photoURL).into(binding.postImg);
                }
                binding.galleryButton.setVisibility(View.VISIBLE);
                binding.cameraButton.setVisibility(View.VISIBLE);
                imagePost = true;
            }
        });


        binding.saveBtn.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            String title = binding.titleEt.getText().toString();
            String desc = binding.descriptionEt.getText().toString();
            imagePost = binding.imagePostSwitch.isChecked();
            String uid = Model.instance().getAuth().getCurrentUser().getUid();

            Post pst = new Post(postId, uid, title, desc, imagePost, photoURL);

            if (imagePost && isImageSelected) {
                binding.postImg.setDrawingCacheEnabled(true);
                binding.postImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.postImg.getDrawable()).getBitmap();

                Model.instance().uploadImage(postId, bitmap, url -> {
                    if (url != null) {
                        pst.setPhotoUrl(url);
                    }
                    Model.instance().addPost(pst, (unused) -> {
                        Navigation.findNavController(view1).popBackStack(R.id.postListFragment, false);
                        binding.progressBar.setVisibility(View.GONE);
                    });
                });
            } else {
                Model.instance().addPost(pst, (unused) -> {
                    Navigation.findNavController(view1).popBackStack(R.id.postListFragment, false);
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