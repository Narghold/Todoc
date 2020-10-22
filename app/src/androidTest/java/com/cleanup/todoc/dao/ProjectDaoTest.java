package com.cleanup.todoc.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    //Set Database
    private TodocDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb(){
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb(){
        database.close();
    }

    //Data set for tests
    private static long PROJECT_ID_1 = 1;
    private static Project PROJECT_TEST_1 = new Project(PROJECT_ID_1, "PROJECT_TEST_1", 255);
    private static long PROJECT_ID_2 = 2;
    private static Project PROJECT_TEST_2 = new Project(PROJECT_ID_2, "PROJECT_TEST_2", 0);

    @Test
    public void insertAndGetProject() throws InterruptedException {
        //Adding project
        this.database.projectDAO().insertProject(PROJECT_TEST_1);
        //Getting project
        Project project = LiveDataTestUtil.getValue(this.database.projectDAO().getProject(PROJECT_TEST_1.getId()));
        //Test
        assertTrue(project.getName().equals(PROJECT_TEST_1.getName()) && project.getId() == PROJECT_ID_1);
    }

    @Test
    public void insertAndGetProjectList() throws InterruptedException {
        //Adding projects
        this.database.projectDAO().insertProject(PROJECT_TEST_1);
        this.database.projectDAO().insertProject(PROJECT_TEST_2);
        //Getting project list
        List<Project> projectList = LiveDataTestUtil.getValue(this.database.projectDAO().getProjectList());
        //Test
        assertFalse(projectList.isEmpty());
        assertTrue(projectList.get(0).getName().equals(PROJECT_TEST_1.getName()) && projectList.get(0).getId() == PROJECT_ID_1);
        assertTrue(projectList.get(1).getName().equals(PROJECT_TEST_2.getName()) && projectList.get(1).getId() == PROJECT_ID_2);

    }

    @Test
    public void insertAndDeleteProject() throws InterruptedException {
        //Adding project
        this.database.projectDAO().insertProject(PROJECT_TEST_1);
        //Getting project
        Project project = LiveDataTestUtil.getValue(this.database.projectDAO().getProject(PROJECT_TEST_1.getId()));
        //Test if project exist
        assertTrue(project.getName().equals(PROJECT_TEST_1.getName()) && project.getId() == PROJECT_ID_1);
        //Delete project
        this.database.projectDAO().deleteProject(PROJECT_ID_1);
        //Getting project list
        List<Project> projectList = LiveDataTestUtil.getValue(this.database.projectDAO().getProjectList());
        //Test
        assertTrue(projectList.isEmpty());
    }
}
