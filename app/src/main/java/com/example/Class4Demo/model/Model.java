package com.example.Class4Demo.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    public static Model instance() {
        return _instance;
    }

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    private Model() {
    }

    public interface GetAllStudentListener {
        void onComplete(List<Student> data);
    }

    public void getAllStudents(GetAllStudentListener callback) {
        executor.execute(() -> {
            List<Student> data = localDb.studentDao().getAll();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainHandler.post(() -> {
                callback.onComplete(data);
            });
        });
    }


    public interface AddStudentListener {
        void onComplete();
    }

    public void addStudent(Student st, AddStudentListener listener) {
        executor.execute(() -> {
            localDb.studentDao().insertAll(st);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainHandler.post(() -> {
                listener.onComplete();
            });
        });
    }


}
