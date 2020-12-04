package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    //Getter for task list in DB
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTaskList();

    //Getter for one task with her id
    @Query("SELECT * FROM Task WHERE id= :taskId")
    LiveData<Task> getTask(long taskId);

    /*  Insert a task in DB
    *   SQL: INSERT INTO Task VALUES task
     */
    @Insert
    void insertTask(Task task);

    //Delete a task with her id
    @Query("DELETE FROM Task WHERE id= :taskId")
    void deleteTask(long taskId);

    //Getter for task with the project attached
    @Query("SELECT * FROM Task WHERE projectId= :projectId")
    LiveData<List<Task>> getTaskListFromProject(long projectId);
}
