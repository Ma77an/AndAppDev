package com.example.Class4Demo.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.Class4Demo.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    private String postId = "";
    private String uid = "";
    private String title = "";
    private String desc = "";
    Boolean isPhoto = false;
    private String photoUrl = "";

    private Long lastUpdated;

    public Post() {
    }

    public Post(@NonNull String postId, String uid, String title, String desc, Boolean isPhoto, String photoUrl) {
        this.postId = postId;
        this.uid = uid;
        this.title = title;
        this.desc = desc;
        this.isPhoto = isPhoto;
        this.photoUrl = photoUrl;
    }

    static final String ID = "postId";
    static final String USER = "uid";
    static final String TITLE = "title";
    static final String DESCRIPTION = "desc";
    static final String IS_PHOTO = "isPhoto";
    static final String PHOTO_URL = "photoURL";
    static final String LAST_UPDATED = "lastUpdated";

    static final String LAST_LOCAL_UPDATED = "posts_last_local_updated";

    static final String COLLECTION = "posts";


    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LAST_LOCAL_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LAST_LOCAL_UPDATED, time);
        editor.commit();
    }


    public static Post fromJson(Map<String, Object> json) {
        String postId = (String) json.get(ID);
        String uid = (String) json.get(USER);
        String title = (String) json.get(TITLE);
        String desc = (String) json.get(DESCRIPTION);
        Boolean isPhoto = (Boolean) json.get(IS_PHOTO);
        String photoUrl = (String) json.get(PHOTO_URL);

        Post post = new Post(postId, uid, title, desc, isPhoto, photoUrl);

        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            post.setLastUpdated(time.getSeconds());
        } catch (Exception ignored) {
        }

        return post;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getPostId());
        json.put(USER, getUid());
        json.put(TITLE, getTitle());
        json.put(DESCRIPTION, getDesc());
        json.put(IS_PHOTO, getPhoto());
        json.put(PHOTO_URL, getPhotoUrl());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());

        return json;
    }


    @NonNull
    public String getPostId() {
        return postId;
    }

    public void setPostId(@NonNull String postId) {
        this.postId = postId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getPhoto() {
        return isPhoto;
    }

    public void setPhoto(Boolean photo) {
        isPhoto = photo;
    }

    public String getPhotoUrl() {
        if (getPhoto()) return photoUrl;
        return "";
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        this.setPhoto(true);
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
