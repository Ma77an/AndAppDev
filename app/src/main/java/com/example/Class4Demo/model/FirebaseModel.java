package com.example.Class4Demo.model;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class FirebaseModel {
    FirebaseFirestore db;
    FirebaseStorage storage;

    FirebaseModel() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new
                FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);

        storage = FirebaseStorage.getInstance();
    }


    public void getAllStudents(Model.Listener<List<Student>> callback) {
        db.collection("students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Student> list = new LinkedList<Student>();
                if (task.isSuccessful()) {
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json : jsonsList) {
                        Student st = Student.fromJson(json.getData());
                        list.add(st);
                    }
                }
                callback.onComplete(list);
            }
        });
    }


    public void getStudentById(String id, Model.Listener<Student> callback) {
        db.collection("students").whereEqualTo("id", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Student st = new Student();
                if (task.isSuccessful()) {
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json : jsonsList) {
                        st = Student.fromJson(json.getData());
                    }
                }
                callback.onComplete(st);
            }
        });
    }


    public void addStudent(Student st, Model.Listener<Void> listener) {
        db.collection("students").document(st.getId()).set(st.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });

    }


    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/" + name + ".jpg");


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });
    }

}
