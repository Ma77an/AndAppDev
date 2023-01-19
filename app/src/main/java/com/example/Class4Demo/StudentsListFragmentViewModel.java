package com.example.Class4Demo;

import androidx.lifecycle.ViewModel;

import com.example.Class4Demo.model.Student;

import java.util.LinkedList;
import java.util.List;

public class StudentsListFragmentViewModel extends ViewModel {
    private List<Student> data = new LinkedList<>();

    List<Student> getData() {
        return data;
    }

    void setData(List<Student> list) {
        data = list;
    }
}
