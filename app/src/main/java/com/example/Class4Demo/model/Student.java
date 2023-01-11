package com.example.Class4Demo.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Student {
    @PrimaryKey
    @NonNull
    private String id = "";
    private String name = "";
    private String bDate = "";
    private String phone = "";
    private String avatar = "";
    private boolean checked = false;

    public Student() {
    }

    public Student(@NonNull String id, String name, String bDate, String phone, String avatar, boolean checked) {
        this.id = id;
        this.name = name;
        this.bDate = bDate;
        this.phone = phone;
        this.avatar = avatar;
        this.checked = checked;
    }


    public static Student fromJson(Map<String, Object> json) {
        String id = (String) json.get("id");
        String name = (String) json.get("name");
        String avatar = (String) json.get("avatar");
        String bdate = (String) json.get("bdate");
        Boolean cb = (Boolean) json.get("cb");
        Student st = new Student(id, name, bdate, "", avatar, cb);
        return st;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("name", getName());
        json.put("avatar", getAvatar());
        json.put("bdate", getBDate());
        json.put("cb", isChecked());

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

    public String getBDate() {
        return bDate;
    }

    public void setBDate(String bDate) {
        this.bDate = bDate;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
