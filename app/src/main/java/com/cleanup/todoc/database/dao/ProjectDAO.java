package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insertProject(Project project);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getUser();

    @Query("DELETE FROM Project WHERE id= :projectId")
    int deleteProject(long projectId);
}
