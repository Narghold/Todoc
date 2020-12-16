package com.cleanup.todoc.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
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
public class TaskDaoTest {

    //Set Database
    private TodocDatabase database;

    //Set projects for tests
    private static final long PROJECT_ID_1 = 1;
    private static final long PROJECT_ID_2 = 2;
    private static final Project PROJECT_1 = new Project("PROJECT_1", 1);
    private static final Project PROJECT_2 = new Project("PROJECT_2", 2);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb(){
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
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
    private static final long TASK_ID_1 = 1;
    private static final Task TASK_TEST_1 = new Task(1, "TASK_TEST_1", 1);
    private static final long TASK_ID_2 = 2;
    private static final Task TASK_TEST_2 = new Task(1, "TASK_TEST_2", 2);
    private static final Task TASK_TEST_3 = new Task(2, "TASK_TEST_3", 3);

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
