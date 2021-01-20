package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    /*  Insert a project in DB
    *   SQL: INSERT INTO Project VALUES project
    */
    @Insert()
    void insertProject(Project project);

    //Getter for project list in DB
    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getProjectList();

    //Getter for one project with his id
    @Query("SELECT * FROM Project WHERE id= :projectId")
    LiveData<Project> getProject(long projectId);

    //Delete one project with his id
    @Query("DELETE FROM Project WHERE id= :projectId")
    void deleteProject(long projectId);
}
