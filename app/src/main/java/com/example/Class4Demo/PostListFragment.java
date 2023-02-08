package com.example.Class4Demo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.Class4Demo.databinding.FragmentPostListBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Post;

public class PostListFragment extends Fragment {

    private PostListViewModel mViewModel;
    FragmentPostListBinding binding;
    PostRecyclerAdapter adapter;

    public static PostListFragment newInstance() {
        return new PostListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        reloadData();

        binding.postsRecyclerView.setHasFixedSize(true);

        binding.postsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostRecyclerAdapter(getLayoutInflater(), mViewModel.getData().getValue());
        binding.postsRecyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new PostRecyclerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "onItemClick: " + pos);
                Post pst = mViewModel.getData().getValue().get(pos);
                PostListFragmentDirections.ActionPostListFragmentToPostDetailsFragment action =
                        PostListFragmentDirections
                                .actionPostListFragmentToPostDetailsFragment(pst.getPostId());
                Navigation.findNavController(view).navigate(action);
//                Toast.makeText(MyApplication.getMyContext(), "Post by: " + pst.getUid(),
//                        Toast.LENGTH_SHORT).show();
            }
        });


        mViewModel.getData().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
//            binding.progressBar.setVisibility(View.GONE);
        });

        Model.instance().EventPostsListLoadingState.observe(getViewLifecycleOwner(), loadingState -> {
            binding.postsSwipeRefresh.setRefreshing(loadingState == Model.LoadingState.LOADING);
        });

        binding.postsSwipeRefresh.setOnRefreshListener(this::reloadData);


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mViewModel = new ViewModelProvider(this).get(PostListViewModel.class);
    }

    void reloadData() {
//        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().refreshAllPosts();
    }
}