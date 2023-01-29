package com.example.Class4Demo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Post;

import java.util.List;

public class UserPostsViewModel extends ViewModel {
    private LiveData<List<Post>> data = Model.instance().getUserPosts();

    LiveData<List<Post>> getData() {
        return data;
    }
}
