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
public class Student {
    @PrimaryKey
    @NonNull
    private String id = "";
    private String name = "";
    private String avatar = "";
    private String birthday = "";
    private String phone = "";
    private String instagram = "";

    private Long lastUpdated;

    public Student() {
    }

    public Student(@NonNull String id, String name, String avatar, String birthday,
                   String phone, String instagram) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.birthday = birthday;
        this.phone = phone;
        this.instagram = instagram;
    }

    static final String ID = "id";
    static final String NAME = "name";
    static final String AVATAR = "avatar";
    static final String BDAY = "birthday";
    static final String PHONE = "phone";
    static final String INSTAGRAM = "instagram";
    static final String LAST_UPDATED = "lastUpdated";

    static final String LAST_LOCAL_UPDATED = "students_last_local_updated";

    static final String COLLECTION = "students";


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


    public static Student fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String name = (String) json.get(NAME);
        String avatar = (String) json.get(AVATAR);
        String bdate = (String) json.get(BDAY);
        String phone = (String) json.get(PHONE);
        String instagram = (String) json.get(INSTAGRAM);

        Student st = new Student(id, name, avatar, bdate, phone, instagram);

        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            st.setLastUpdated(time.getSeconds());
        } catch (Exception ignored) {
        }

        return st;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(NAME, getName());
        json.put(AVATAR, getAvatar());
        json.put(BDAY, getBirthday());
        json.put(PHONE, getPhone());
        json.put(INSTAGRAM, getInstagram());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());

        return json;
    }


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String bDate) {
        this.birthday = bDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }


    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
