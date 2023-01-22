package com.example.Class4Demo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Student;

import java.util.List;

public class StudentsListFragmentViewModel extends ViewModel {
    private LiveData<List<Student>> data = Model.instance().getAllStudents();

    LiveData<List<Student>> getData() {
        return data;
    }

}
