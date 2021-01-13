package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    //Instantiate
    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    //Insert task
    public void createTask(Task task){
        taskDao.insertTask(task);
    }

    //Get task
    public LiveData<Task> getTask(long taskId){
        return taskDao.getTask(taskId);
    }

    //Get task list
    public LiveData<List<Task>> getTaskList(){
        return taskDao.getTaskList();
    }

    //Get task list from project ID
    public LiveData<List<Task>> getTaskListFromProject(long projectId){
        return taskDao.getTaskListFromProject(projectId);
    }

    //Delete task
    public void deleteTask(long taskId){
        taskDao.deleteTask(taskId);
    }

    //Clear task list
    public void clearTaskList(){
        taskDao.clearTaskList();
    }
}
