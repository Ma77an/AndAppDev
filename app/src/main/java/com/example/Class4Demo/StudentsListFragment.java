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

import com.example.Class4Demo.databinding.FragmentStudentsListBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Student;

public class StudentsListFragment extends Fragment {
    StudentRecyclerAdapter adapter;
    FragmentStudentsListBinding binding;
    StudentsListFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStudentsListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        reloadData();

        binding.recyclerView.setHasFixedSize(true);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StudentRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StudentRecyclerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "onItemClick: " + pos);
                Student st = viewModel.getData().getValue().get(pos);
                StudentsListFragmentDirections.ActionStudentsListFragmentToBlueFragment action
                        = StudentsListFragmentDirections.actionStudentsListFragmentToBlueFragment(st.getName(), st.getId());
                Navigation.findNavController(view).navigate(action);
            }
        });

//        NavDirections action = StudentsListFragmentDirections.actionGlobalAddStudentFragment("0147896325");
//        binding.btnAdd.setOnClickListener(Navigation.createNavigateOnClickListener(action));

        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
//            binding.progressBar.setVisibility(View.GONE);
        });

        Model.instance().EventStudentsListLoadingState.observe(getViewLifecycleOwner(), loadingState -> {
            binding.swipeRefresh.setRefreshing(loadingState == Model.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(this::reloadData);


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(StudentsListFragmentViewModel.class);
    }

    void reloadData() {
//        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().refreshAllStudents();
    }
}