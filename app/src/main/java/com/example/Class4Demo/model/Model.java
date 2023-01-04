package com.example.Class4Demo.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    public static Model instance() {
        return _instance;
    }

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    private Model() {
    }

    public interface GetAllStudentListener {
        void onComplete(List<Student> data);
    }

    public void getAllStudents(GetAllStudentListener callback) {
        executor.execute(() -> {
            List<Student> data = localDb.studentDao().getAll();
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            mainHandler.post(() -> {
                callback.onComplete(data);
            });
        });
    }

    public interface getStudentByIdListener {
        void onComplete(Student st);
    }

    public void getStudentById(String id, getStudentByIdListener callback) {
        executor.execute(() -> {
            Student st = localDb.studentDao().getStudentById(id);
            Log.d("TAG", "getStudentById: " + st.getName());
            mainHandler.post(() -> {
                callback.onComplete(st);
            });
        });
    }


    public interface AddStudentListener {
        void onComplete();
    }

    public void addStudent(Student st, AddStudentListener listener) {
        executor.execute(() -> {
            localDb.studentDao().insertAll(st);
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            mainHandler.post(() -> {
                listener.onComplete();
            });
        });
    }


}
