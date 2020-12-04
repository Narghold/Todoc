package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    //Instantiate
    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    //Insert project
    public void createProject(Project project){
        projectDao.insertProject(project);
    }

    //Get project
    public LiveData<Project> getProject(long projectId){
        return this.projectDao.getProject(projectId);
    }

    //Get project list
    public LiveData<List<Project>> getProjectList(){
        return this.projectDao.getProjectList();
    }

    //Delete project
    public void deleteProject(long projectId){
        projectDao.deleteProject(projectId);
    }
}
