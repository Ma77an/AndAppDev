package com.example.Class4Demo.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.Class4Demo.MyApplication;
import com.example.Class4Demo.StudentDao;

@Database(entities = {Student.class}, version = 55)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract StudentDao studentDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(), AppLocalDbRepository.class,
                "dbFileName.db").fallbackToDestructiveMigration().build();
    }

    private AppLocalDb() {
    }
}