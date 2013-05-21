package org.beachbench;

import org.beachbench.exception.ExceptionUtils;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.UUID.randomUUID;

class Benchmark {
    private final List<TestCase> testCases = new LinkedList<TestCase>();
    private final String id = randomUUID().toString();
    private String name = "";
    private final Map<String, Object> settings = new HashMap<String, Object>();

    public void add(TestCase testCase) {
        testCases.add(testCase);
    }

    public void run() {
        System.out.println(String.format("BeachBench > Starting benchmark [%s] and id [%s] with a total of %s testcases", name, id, testCases.size()));

        for (TestCase testCase : testCases) {
            testCase.clearResults();
        }

        long startMs = System.currentTimeMillis();
        int testCaseIteration = 1;

        for (TestCase testCase : testCases) {

            System.out.println("BeachBench > =======================================================");
            System.out.println(String.format("BeachBench > Starting testcase [%s/%s] '%s'", testCaseIteration, testCases.size(), testCase.getName()));
            System.out.println(String.format("BeachBench > Driver [%s]", testCase.getDriver().getName()));

            BenchmarkDriver driver;

            try {
                driver = testCase.getDriver().newInstance();
            } catch (Throwable e) {
                throw ExceptionUtils.wrap(e);
            }

            //Map<String, Object> settings = testCase.getSettings();
            //populateDriver(settings, driver);

            if (testCase.getWarmupRunIterationCount() > 0) {
                System.out.println(String.format("BeachBench > Starting warmup with a total of %s iterations", testCase.getWarmupRunIterationCount()));
                for (long iteration = 1; iteration <= testCase.getWarmupRunIterationCount(); iteration++) {
                    runTestCase(testCase, driver, iteration, testCase.getWarmupRunIterationCount(), true);
                }
                System.out.println("BeachBench > Finished warmup");
            } else {
                System.out.println("BeachBench > Skipping warmup");
            }

            System.out.println("BeachBench > -------------------------------------------------------");
            System.out.println(String.format("BeachBench > Executing a total of %s benchmark iterations", testCase.getTestCaseIterationCount()));

            for (long iteration = 1; iteration <= testCase.getTestCaseIterationCount(); iteration++) {
                TestCaseResult result = runTestCase(testCase, driver, iteration, testCase.getTestCaseIterationCount(), false);
                testCase.add(result);
            }
            testCaseIteration++;

        }

        System.out.println("BeachBench > =======================================================");
        long durationMs = System.currentTimeMillis() - startMs;
        //this.durationMs = durationMs
        System.out.println(String.format("BeachBench > Benchmark completed in %s seconds", durationMs));
    }

    private TestCaseResult runTestCase(TestCase testCase, BenchmarkDriver driver, long iteration, long maxIterations, boolean warmup) {
        System.out.println("BeachBench > -------------------------------------------------------");
        if (warmup) {
            System.out.println(String.format("BeachBench > Running warmup iteration [%s/%s] for testcase [%s]", iteration, maxIterations, testCase.getName()));
        } else {
            System.out.println(String.format("BeachBench > Running iteration [%s/%s] for testcase [%s]", iteration, maxIterations, testCase.getName()));
        }
        TestCaseResult result = new TestCaseResult(testCase);
        System.out.println("BeachBench > Setting up driver");


        boolean timed = testCase.getDurationInSeconds() < Long.MAX_VALUE;

        ScheduledThreadPoolExecutor scheduledExecutor = null;
        if (timed) {
            System.out.println(String.format("BeachBench > Running for %s seconds", testCase.getDurationInSeconds()));
            scheduledExecutor = new ScheduledThreadPoolExecutor(1);
            scheduledExecutor.schedule(new ShutdownRunnable(driver), testCase.getDurationInSeconds(), TimeUnit.SECONDS);
        }

        driver.init();
        driver.setUp();

        Date startDate = new Date();
        long startMs = System.currentTimeMillis();

        System.out.println("BeachBench > Starting benchmark");
        Throwable thrown = null;
        try {
            driver.run(result);
        } catch (Throwable t) {
            System.out.println("BeachBench > An exception was thrown, continuing to the next testcase");
            t.printStackTrace();
            thrown = t;
        }
        long endMs = System.currentTimeMillis();
        long durationMs = endMs - startMs;

        System.out.println(String.format("BeachBench > Testcase took %s seconds", durationMs / 1000));

        result.setTestCaseIteration(iteration);
        result.setThrown(thrown);
        result.setStart(startMs);
        result.setEnd(endMs);
        result.setDuration(durationMs);
        result.setDate(startDate);

        if (thrown == null) {
            System.out.println("BeachBench > Processing results");
            driver.processResults(result);
        }
        System.out.println("BeachBench > Tearing down driver");
        driver.tearDown();
        if (timed) {
            System.out.println("BeachBench > Waiting for benchmark to complete");
            scheduledExecutor.shutdown();
            try {
                scheduledExecutor.awaitTermination(3600, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw ExceptionUtils.wrap(e);
            }
        }

        return result;
    }

    private class ShutdownRunnable implements Runnable {
        private final BenchmarkDriver driver;

        ShutdownRunnable(BenchmarkDriver driver) {
            this.driver = driver;
        }

        public void run() {
            System.out.println("BeachBench > Shutting down driver");
            driver.shutdown();
        }
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    List<TestCase> getTestCases() {
        return testCases;
    }

    /*    private void populateDriver(Map<String, Object> settings, BenchmarkDriver driver) {
        for (def entry in settings.entrySet()){
            def property = entry.key
            def value = entry.value
            try {
                Field field = driver.class.getDeclaredField(property)
                field.setAccessible true
                field.set(driver, value)
            } catch (NoSuchFieldException ignore) {
            }
        }
    }*/
}
