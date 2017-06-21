package pl.noip.piekaa.bondaruktuiwaniuk2.services.asyncTaskRelated;

import org.junit.Before;
import org.junit.Test;

import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageException;

import static org.junit.Assert.*;

/**
 * Created by piekaa on 2017-06-17.
 */
public class DoubleTaskTest
{

    DoubleTask doubleTask;

    @Before
    public void init()
    {
        doubleTask = new DoubleTask();
        succeed = false;
        failed = false;
    }


    boolean succeed;
    boolean failed;


    Object object;

    @Test
    public void executeTaskObject() throws Exception
    {
        doubleTask = new DoubleTask();
        doubleTask.executeTaskObject(
                obj -> {succeed = true;
                        object = obj;},
                obj -> {failed = true;},
                () -> {return new Object();},
                () -> {throw new MessageException();}

        );

        assertTrue(succeed);
        assertFalse(failed);
        assertNotNull(object);
        doubleTask = new DoubleTask();
        doubleTask.executeTaskObject(
                obj -> {succeed = true;
                    object = obj;},
                obj -> {failed = true;},
                () -> {return new Object();},
                () -> {throw new MessageException();}

        );

        assertTrue(succeed);
        assertFalse(failed);
        assertNotNull(object);
        doubleTask = new DoubleTask();
        doubleTask.executeTaskObject(
                obj -> {succeed = true;
                    object = obj;},
                obj -> {failed = true;},
                () -> {return new Object();},
                () -> {throw new MessageException();}

        );

        assertTrue(succeed);
        assertFalse(failed);
        assertNotNull(object);
    }

    @Test
    public void executeTaskVoid() throws Exception
    {
        doubleTask = new DoubleTask();
        doubleTask.executeTaskVoid(
                obj -> {succeed = true;},
                obj -> {failed = true;},
                () -> {},
                () -> {throw new MessageException();}

        );

        assertTrue(succeed);
        assertFalse(failed);
        doubleTask = new DoubleTask();
        doubleTask.executeTaskVoid(
                obj -> {succeed = true;},
                obj -> {failed = true;},
                () -> {},
                () -> {throw new MessageException();}

        );

        assertTrue(succeed);
        assertFalse(failed);
        doubleTask = new DoubleTask();
        doubleTask.executeTaskVoid(
                obj -> {succeed = true;},
                obj -> {failed = true;},
                () -> {},
                () -> {throw new MessageException();}

        );

        assertTrue(succeed);
        assertFalse(failed);
    }

}