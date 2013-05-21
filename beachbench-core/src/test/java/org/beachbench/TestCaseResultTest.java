package org.beachbench;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCaseResultTest {

    /*
        private String id;
    private long testCaseIteration;
    private Throwable thrown;
    private long start;
    private long end;
    private long duration;
    private Date date;

    private final TestCase testCase;
     */


    private TestCaseResult testCaseResult;

    @Mock
    private TestCase testCase;

    private final Date date = new Date();
    private final Throwable throwable = new Throwable();


    @Before
    public void setup(){
        testCaseResult = new TestCaseResult(testCase);
        testCaseResult.setStart(10);
        testCaseResult.setEnd(30);
        testCaseResult.setDuration(20);
        testCaseResult.setDate(date);
        testCaseResult.setThrown(throwable);
        testCaseResult.setTestCaseIteration(40);
    }

    @Test
    public void test(){
        assertNotNull(testCaseResult.getId());
        assertEquals(10, testCaseResult.getStart());
        assertEquals(30, testCaseResult.getEnd());
        assertEquals(20, testCaseResult.getDuration());
        assertEquals(date, testCaseResult.getDate());
        assertEquals(throwable, testCaseResult.getThrown());
        assertNotNull(testCaseResult.getTestCase());
        assertEquals(40,testCaseResult.getTestCaseIteration());
    }

    @Test(expected = RuntimeException.class)
    public void testNullTestCase(){
        testCaseResult = new TestCaseResult(null);
    }
}
