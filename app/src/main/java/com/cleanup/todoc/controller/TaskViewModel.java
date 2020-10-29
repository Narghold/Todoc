package com.cleanup.todoc.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    //Repositories
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public LiveData<List<Task>> getTaskList(){
        return taskDataSource.getTaskList();
    }

    public void createTask(Task task){
        executor.execute(() -> {
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(long taskId){
        executor.execute(() -> {
            taskDataSource.deleteTask(taskId);
        });
    }

    public LiveData<List<Project>> getProjectList(){
        return projectDataSource.getProjectList();
    }

    public void createProject(Project project){
        executor.execute(() -> {
            projectDataSource.createProject(project);
        });
    }
}
