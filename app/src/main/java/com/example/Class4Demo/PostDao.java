package com.example.Class4Demo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.Class4Demo.model.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Query("select * from Post order by lastUpdated desc")
    LiveData<List<Post>> getAll();

    @Query("select * from Post where postId=:postId")
    Post getPosyById(String postId);

    @Query("select * from Post where uid=:uid order by lastUpdated desc")
    LiveData<List<Post>> getStudentPosts(String uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);
}
