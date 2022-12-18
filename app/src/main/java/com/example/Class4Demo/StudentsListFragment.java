package com.example.Class4Demo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Student;

import java.util.List;

public class StudentsListFragment extends Fragment {
    List<Student> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_students_list, container, false);
        data = Model.instance().getAllStudents();
        RecyclerView list = view.findViewById(R.id.studentlistfrag_list);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        StudentRecyclerAdapter adapter = new StudentRecyclerAdapter(getLayoutInflater(), data);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new StudentRecyclerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "onItemClick: " + pos);
                Student st = data.get(pos);
                StudentsListFragmentDirections.ActionStudentsListFragmentToBlueFragment action
                        = StudentsListFragmentDirections.actionStudentsListFragmentToBlueFragment(st.name);
                Navigation.findNavController(view).navigate(action);
            }
        });

        View addBtn = view.findViewById(R.id.studentslistfrag_add_btn);
        addBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_studentsListFragment_to_blueFragment));
        return view;
    }
}