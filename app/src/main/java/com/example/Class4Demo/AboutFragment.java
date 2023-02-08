package com.example.Class4Demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.Class4Demo.databinding.FragmentAboutBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Post;
import com.example.Class4Demo.model.Student;
import com.squareup.picasso.Picasso;


public class AboutFragment extends Fragment {

    FragmentAboutBinding binding;
    PostRecyclerAdapter adapter;
    String uid;
    private UserPostsViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.aboutFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, this, Lifecycle.State.RESUMED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        uid = Model.instance().getAuth().getUid();
        Model.instance().getStudentById(uid, st -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            studentDetails(st);
            binding.progressBar.setVisibility(View.GONE);
        });

        binding.editBtn.setOnClickListener(v -> {
            com.example.Class4Demo.AboutFragmentDirections.ActionAboutFragmentToEditStudentFragment action
                    = AboutFragmentDirections.actionAboutFragmentToEditStudentFragment(uid);
            Navigation.findNavController(v).navigate(action);
        });

        binding.signOutBtn.setOnClickListener(v -> {
            Model.instance().getAuth().signOut();
            mViewModel.clearData();


            Intent i = new Intent(MyApplication.getMyContext(), WelcomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            getActivity().finish();
        });

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
                com.example.Class4Demo.AboutFragmentDirections.ActionAboutFragmentToPostDetailsFragment action =
                        AboutFragmentDirections.actionAboutFragmentToPostDetailsFragment(pst.getPostId());
                Navigation.findNavController(view).navigate(action);
            }
        });


        mViewModel.getData().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
        });

        Model.instance().EventPostsListLoadingState.observe(getViewLifecycleOwner(), loadingState -> {
            binding.postsSwipeRefresh.setRefreshing(loadingState == Model.LoadingState.LOADING);
        });

        binding.postsSwipeRefresh.setOnRefreshListener(this::reloadData);

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mViewModel = new ViewModelProvider(this).get(UserPostsViewModel.class);
    }

    void reloadData() {
        Model.instance().refreshAllPosts();
    }
}