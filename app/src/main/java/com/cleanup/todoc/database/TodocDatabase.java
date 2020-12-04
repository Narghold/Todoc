package com.cleanup.todoc.database;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.ContentValues;
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
                            .addCallback(prepopulateDatabase)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //Prepopulate DB with different projects
    private static final RoomDatabase.Callback prepopulateDatabase = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            ContentValues projectTartampion = new ContentValues();
            projectTartampion.put("name" , "Projet Tartampion");
            projectTartampion.put("color" , 0xFFEADAD1);
            db.insert("Project", OnConflictStrategy.IGNORE, projectTartampion);

            ContentValues projectLucidia = new ContentValues();
            projectLucidia.put("name" , "Projet Lucidia");
            projectLucidia.put("color" , 0xFFB4CDBA);
            db.insert("Project", OnConflictStrategy.IGNORE, projectLucidia);

            ContentValues projectCircus = new ContentValues();
            projectCircus.put("name" , "Projet Circus");
            projectCircus.put("color" , 0xFFA3CED2);
            db.insert("Project", OnConflictStrategy.IGNORE, projectCircus);
        }
    };
}
