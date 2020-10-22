package com.cleanup.todoc.DI;

import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DI {

    public static TaskDataRepository provideTaskDataSource(Context context){
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDAO());
    }

    public static ProjectDataRepository provideProjectDataSource(Context context){
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDAO());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        TaskDataRepository taskDataSource = provideTaskDataSource(context);
        ProjectDataRepository projectDataSource = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(taskDataSource, projectDataSource, executor);
    }
}
