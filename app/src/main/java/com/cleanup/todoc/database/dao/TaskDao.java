package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTaskList();

    @Query("SELECT * FROM Task WHERE id= :taskId")
    LiveData<Task> getTask(long taskId);

    @Insert
    void insertTask(Task task);

    @Query("DELETE FROM Task WHERE id= :taskId")
    void deleteTask(long taskId);

    @Query("SELECT * FROM Task WHERE projectId= :projectId")
    LiveData<List<Task>> getTaskListFromProject(long projectId);
}
