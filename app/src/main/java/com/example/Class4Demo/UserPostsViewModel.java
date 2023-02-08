package com.example.Class4Demo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.Class4Demo.model.Model;
import com.example.Class4Demo.model.Post;

import java.util.List;

public class UserPostsViewModel extends ViewModel {
    private static LiveData<List<Post>> data;


    LiveData<List<Post>> getData() {
        if (data == null) {
            data = Model.instance().getUserPosts();
        }
        return data;
    }

    public void setData(LiveData<List<Post>> data) {
        UserPostsViewModel.data = data;
    }

    public void clearData() {
        Model.instance().refreshUserPosts();
        UserPostsViewModel.data = null;
    }

    public static void refresh() {
        Model.instance().refreshUserPosts();
        data = Model.instance().getUserPosts();
    }
}