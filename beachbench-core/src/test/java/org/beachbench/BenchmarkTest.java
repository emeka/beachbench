package org.beachbench;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BenchmarkTest {

    private Benchmark benchmark;

    private org.beachbench.TestCase testCase1, testCase2;

    @Before
    public void setup(){
        testCase1 = new TestCase();
        testCase1.setName("testCase1");
        testCase1.setDriver(TestDriver1.class);
        testCase1.setWarmupRunIterationCount(0);
        testCase1.setTestCaseIterationCount(1);
        testCase1.setDurationInSeconds(1);

        testCase2 = new TestCase();
        testCase2.setName("testCase2");
        testCase2.setDriver(TestDriver2.class);
        testCase2.setWarmupRunIterationCount(20);
        testCase2.setTestCaseIterationCount(100);


        benchmark = new Benchmark();
        benchmark.setName("benchmark");
        benchmark.add(testCase1);
        benchmark.add(testCase2);
    }

    @Test
    public void testId(){
        assertNotNull(benchmark.getId());
        assertFalse(benchmark.getId().isEmpty());
        assertNotNull(benchmark.getTestCases());
        assertEquals("benchmark", benchmark.getName());
    }

    @Test
    public void testNoTestCase(){
        benchmark = new Benchmark();
        try{
            benchmark.run();
        } catch(Throwable e){
            fail();
        }
    }

    @Test
    public void testWithTestCase(){
        try{
            benchmark.run();
        } catch(Throwable e){
            fail();
        }
    }

    static class TestDriver1 extends BenchmarkDriver{
        @Override
        public void run(TestCaseResult result) {

        }
    }

    static class TestDriver2 extends BenchmarkDriver{
        @Override
        public void run(TestCaseResult result) {

        }
    }
}
