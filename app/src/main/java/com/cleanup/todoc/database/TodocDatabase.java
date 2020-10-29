package com.cleanup.todoc.database;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.util.Log;

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
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d("Callback" , "Callback passed");

                                    Project[] projectTab = new Project[]{
                                            new Project("Projet Tartampion", 0xFFEADAD1),
                                            new Project("Projet Lucidia", 0xFFB4CDBA),
                                            new Project("Projet Circus", 0xFFA3CED2),
                                    };

                                    for (Project project : projectTab) {
                                        INSTANCE.projectDAO().insertProject(project);
                                    }
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
