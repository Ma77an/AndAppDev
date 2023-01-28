package com.example.Class4Demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.Class4Demo.databinding.FragmentPostDetailsBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Post;
import com.squareup.picasso.Picasso;

public class PostDetailsFragment extends Fragment {

    FragmentPostDetailsBinding binding;
    String postId;
    Post post;

    public PostDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postId = getArguments().getString("postId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Model.instance().getPostById(postId, pst -> {
            post = pst;
            binding.postProgressBar.setVisibility(View.VISIBLE);
            if (Model.instance().getAuth().getCurrentUser().getUid().equals(pst.getUid())) {
                binding.editBtn.setVisibility(View.VISIBLE);
            }
            binding.title.setText(pst.getTitle());
            if (pst.getPhoto() && !pst.getPhotoUrl().equals("")) {
                Picasso.get().load(pst.getPhotoUrl()).into(binding.imageView);
            } else {
                binding.imageView.setVisibility(View.GONE);
            }
            binding.descTv.setText(pst.getDesc());

            Model.instance().getStudentById(pst.getUid(), st -> {
                binding.stName.setText(st.getName());
                if (!st.getAvatar().equals("")) {
                    Picasso.get().load(st.getAvatar()).placeholder(R.drawable.avatar).into(binding.stIv);
                } else {
                    binding.stIv.setImageResource(R.drawable.avatar);
                }
            });
            binding.postProgressBar.setVisibility(View.GONE);
        });

        binding.linearLayout2.setOnClickListener(v -> {
            if (post != null) {
                com.example.Class4Demo.PostDetailsFragmentDirections
                        .ActionPostDetailsFragmentToBlueFragment action =
                        PostDetailsFragmentDirections.
                                actionPostDetailsFragmentToBlueFragment(post.getUid());
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }
}