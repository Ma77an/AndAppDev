package com.example.Class4Demo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.Class4Demo.databinding.FragmentStudentsListBinding;
import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Student;

import java.util.LinkedList;
import java.util.List;

public class StudentsListFragment extends Fragment {
    List<Student> data = new LinkedList<>();
    StudentRecyclerAdapter adapter;
    FragmentStudentsListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStudentsListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        reloadData();

        binding.recyclerView.setHasFixedSize(true);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StudentRecyclerAdapter(getLayoutInflater(), data);
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StudentRecyclerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "onItemClick: " + pos);
                Student st = data.get(pos);
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
    public void onResume() {
        super.onResume();
        reloadData();
    }

    void reloadData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllStudents(studentList -> {
            data = studentList;
            adapter.setData(data);
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}