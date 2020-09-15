package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    //DAO
    public abstract ProjectDao projectDAO();
    public abstract TaskDao taskDAO();

    //SINGLETON
    private static volatile TodocDatabase INSTANCE;

    //INSTANCE
    public static TodocDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (TodocDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "TodocBDD.db")
                            .addCallback(prepopulateDb())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDb(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues mContentValues = new ContentValues();
                mContentValues.put("id", 1);
                mContentValues.put("name", "Tartampion");
                mContentValues.put("color", 0xFFEADAD1);

                db.insert("Project", OnConflictStrategy.IGNORE, mContentValues);
            }
        };
    }


}
