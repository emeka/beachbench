package org.beachbench;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCaseTest {
    private TestCase testCase;
    private final Class<BenchmarkDriver> benchmarkDriver = BenchmarkDriver.class;;

    @Mock
    private TestCaseResult testCaseResult1, testCaseResult2;

    @Before
    public void setup(){
        testCase = new TestCase();
        testCase.setName("name");
        testCase.setWarmupRunIterationCount(111);
        testCase.setTestCaseIterationCount(222);
        testCase.setDurationInSeconds(33);
        testCase.setDriver(benchmarkDriver);
        testCase.add(testCaseResult1);
        testCase.add(testCaseResult2);
    }


    @Test
    public void test(){
        assertNotNull(testCase.getId());
        assertFalse(testCase.getId().isEmpty());
        assertEquals(111, testCase.getWarmupRunIterationCount());
        assertEquals(222, testCase.getTestCaseIterationCount());
        assertEquals(33, testCase.getDurationInSeconds());
        assertEquals(benchmarkDriver,testCase.getDriver());
        assertEquals(2, testCase.getResults().size());
        assertEquals(testCaseResult1,testCase.getResults().get(0));
        assertEquals(testCaseResult2,testCase.getResults().get(1));
    }

    @Test(expected = RuntimeException.class)
    public void testNullDriver(){
        testCase.setDriver(null);
    }

    @Test(expected = RuntimeException.class)
    public void testNullResult(){
        testCase.add(null);
    }

    private static class TestDriver extends BenchmarkDriver{
        @Override
        public void run(TestCaseResult result) {

        }
    }
}
