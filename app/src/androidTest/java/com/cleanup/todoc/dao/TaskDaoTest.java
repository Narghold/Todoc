package com.cleanup.todoc.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Time;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    //Set Database
    private TodocDatabase database;

    //Set projects for tests
    private static long PROJECT_ID_1 = 1;
    private static Project PROJECT_1 = new Project(PROJECT_ID_1, "PROJECT_1", 1);
    private static Project PROJECT_2 = new Project(2, "PROJECT_2", 2);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb(){
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        this.database.projectDAO().insertProject(PROJECT_1);
        this.database.projectDAO().insertProject(PROJECT_2);
    }

    @After
    public void closeDb(){
        database.close();
    }

    //Data set for tests
    private static long TASK_ID_1 = 1;
    private static Task TASK_TEST_1 = new Task(TASK_ID_1, 1, "TASK_TEST_1", 1);
    private static long TASK_ID_2 = 2;
    private static Task TASK_TEST_2 = new Task(TASK_ID_2, 1, "TASK_TEST_2", 2);
    private static long TASK_ID_3 = 3;
    private static Task TASK_TEST_3 = new Task(TASK_ID_3, 2, "TASK_TEST_3", 3);

    @Test
    public void insertAndGetTask() throws InterruptedException {
        //Adding task
        this.database.taskDAO().insertTask(TASK_TEST_1);
        //Getting task
        Task task = LiveDataTestUtil.getValue(this.database.taskDAO().getTask(TASK_ID_1));
        //Test
        assertTrue(task.getName().equals(TASK_TEST_1.getName()) && task.getId() == TASK_ID_1);
    }

    @Test
    public void insertAndGetTaskList() throws InterruptedException {
        //Adding tasks
        this.database.taskDAO().insertTask(TASK_TEST_1);
        this.database.taskDAO().insertTask(TASK_TEST_2);
        //Getting task list
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDAO().getTaskList());
        //Test
        assertFalse(taskList.isEmpty());
        assertTrue(taskList.get(0).getName().equals(TASK_TEST_1.getName()) && taskList.get(0).getId() == TASK_ID_1);
        assertTrue(taskList.get(1).getName().equals(TASK_TEST_2.getName()) && taskList.get(1).getId() == TASK_ID_2);
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        //Adding task
        this.database.taskDAO().insertTask(TASK_TEST_1);
        //Getting task
        Task task = LiveDataTestUtil.getValue(this.database.taskDAO().getTask(TASK_ID_1));
        //Test if task exist
        assertTrue(task.getName().equals(TASK_TEST_1.getName()) && task.getId() == TASK_ID_1);
        //Delete task
        this.database.taskDAO().deleteTask(TASK_ID_1);
        //Getting task list
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDAO().getTaskList());
        //Test
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void insertAndGetTaskFromProject() throws InterruptedException {
        //Adding tasks
        this.database.taskDAO().insertTask(TASK_TEST_1);
        this.database.taskDAO().insertTask(TASK_TEST_2);
        this.database.taskDAO().insertTask(TASK_TEST_3);
        //Getting task list from project
        List<Task> taskListFromProject = LiveDataTestUtil.getValue(this.database.taskDAO().getTaskListFromProject(PROJECT_ID_1));
        //Test
        assertFalse(taskListFromProject.isEmpty());
        assertTrue(taskListFromProject.get(0).getName().equals(TASK_TEST_1.getName()) && taskListFromProject.get(0).getId() == TASK_ID_1);
        assertTrue(taskListFromProject.get(1).getName().equals(TASK_TEST_2.getName()) && taskListFromProject.get(1).getId() == TASK_ID_2);
        assertFalse(taskListFromProject.contains(TASK_TEST_3));
    }

}