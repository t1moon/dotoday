package com.inc.tim.dotoday;

import com.inc.tim.dotoday.tasks.TasksActivity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test public void mainActivity_shouldNotBeNull() throws Exception {
        TasksActivity tasksActivity = new TasksActivity();
        assertNotNull(tasksActivity);
    }
}