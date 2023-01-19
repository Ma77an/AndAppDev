package com.example.Class4Demo.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();


    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private final FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static Model instance() {
        return _instance;
    }

    private Model() {
    }


    public interface Listener<T> {
        void onComplete(T data);
    }


    public void getAllStudents(Listener<List<Student>> callback) {
        //get local last update
        Long localLastUpdate = Student.getLocalLastUpdate();

        //get all updated records from firebase since last local update
        firebaseModel.getAllStudentsSince(localLastUpdate, list -> {
            executor.execute(() -> {
                Log.d("TAG", "firebase returned: " + list.size());
                Long time = localLastUpdate;
                for (Student st : list) {
                    //insert new records into ROOM
                    localDb.studentDao().insertAll(st);
                    if (time < st.getLastUpdated())
                        time = st.getLastUpdated();
                }
                //update last local update
                Student.setLocalLastUpdate(time);

                //return complete list from ROOM
                List<Student> complete = localDb.studentDao().getAll();
                mainHandler.post(() -> {
                    callback.onComplete(complete);
                });
            });
        });
    }


    public void getStudentById(String id, Listener<Student> callback) {
        firebaseModel.getStudentById(id, callback);
//        executor.execute(() -> {
//            Student st = localDb.studentDao().getStudentById(id);
//            Log.d("TAG", "getStudentById: " + st.getName());
//            mainHandler.post(() -> callback.onComplete(st));
//        });
    }


    public void addStudent(Student st, Listener<Void> listener) {
        firebaseModel.addStudent(st, listener);
//        executor.execute(() -> {
//            localDb.studentDao().insertAll(st);
////            try {
////                Thread.sleep(2000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//            mainHandler.post(listener::onComplete);
//        });
    }


    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name, bitmap, listener);
    }
}
