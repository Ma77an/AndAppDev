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
import androidx.navigation.NavDirections;
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
        adapter = new StudentRecyclerAdapter(getLayoutInflater(), viewModel.getData());
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StudentRecyclerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "onItemClick: " + pos);
                Student st = viewModel.getData().get(pos);
                StudentsListFragmentDirections.ActionStudentsListFragmentToBlueFragment action
                        = StudentsListFragmentDirections.actionStudentsListFragmentToBlueFragment(st.getName(), st.getId());
                Navigation.findNavController(view).navigate(action);
            }
        });

        NavDirections action = StudentsListFragmentDirections.actionGlobalAddStudentFragment();
        binding.btnAdd.setOnClickListener(Navigation.createNavigateOnClickListener(action));
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(StudentsListFragmentViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    void reloadData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllStudents(studentList -> {
            viewModel.setData(studentList);
            adapter.setData(viewModel.getData());
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}