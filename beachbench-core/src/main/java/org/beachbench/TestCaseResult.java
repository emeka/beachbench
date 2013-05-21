package org.beachbench;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.UUID.randomUUID;

/**
 * Contains the result of a TestCase execution. Essentially it is a key/value map so that it can store any kind of result.
 *
 * TestCaseResult is not threadsafe.
 *
 * @author Peter Veentjer.
 */
public class TestCaseResult {

    private static final String PROPERTY_ID = "id";
    private static final String PROPERTY_TEST_CASE_ITERATION = "testCaseIteration";
    private static final String PROPERTY_THROWN = "thrown";
    private static final String PROPERTY_START = "start";
    private static final String PROPERTY_END = "end";
    private static final String PROPERTY_DURATION = "duration[ms]";
    private static final String PROPERTY_DATE = "date";

    private final TestCase testCase;
    private final Map<String, Object> properties = new HashMap<String, Object>();

    /**
     * Creates a new TestCaseResult.
     *
     * @param testCase the TestCase this TestCaseResult belongs to.
     * @throws NullPointerException if testCase is null.
     */
    public TestCaseResult(TestCase testCase) {
        if(testCase == null)throw new NullPointerException();
        this.testCase = testCase;
        put(PROPERTY_ID, randomUUID().toString());
    }

    /**
     * Gets a property with a specific name. Null is returned if nothing is found.
     *
     * @param name the name of the property.
     * @return the value, or null of nothing is found.
     */
    public Object get(String name) {
        return properties.get(name);
    }

    /**
     * Sets a property value. If the property already exists, it is overwritten.
     *
     * @param name the name of the property
     * @param value the value of the property.
     * @throws NullPointerException if name is null.
     */
    public void put(String name, Object value) {
        if(name == null)throw new NullPointerException();
        properties.put(name, value);
    }

    /**
     * Gets the TestCase this TestCaseResult belongs to.
     *
     * @return the TestCase this TestCaseResult belongs to.
     */
    public TestCase getTestCase() {
        return testCase;
    }

    public String getId() {
        return (String)get(PROPERTY_ID);
    }

    public long getTestCaseIteration() {
        return ((Long)get(PROPERTY_TEST_CASE_ITERATION)).longValue();
    }

    public void setTestCaseIteration(long testCaseIteration) {
        put(PROPERTY_TEST_CASE_ITERATION, testCaseIteration);
    }

    public Throwable getThrown() {
        return (Throwable)get(PROPERTY_THROWN);
    }

    public void setThrown(Throwable thrown) {
        put(PROPERTY_THROWN, thrown);
    }

    public long getStart() {
        return ((Long)get(PROPERTY_START)).longValue();
    }

    public void setStart(long start) {
        put(PROPERTY_START, start);
    }

    public long getEnd() {
        return ((Long)get(PROPERTY_END)).longValue();
    }

    public void setEnd(long end) {
        put(PROPERTY_END, end);
    }

    public long getDuration() {
        return ((Long)get(PROPERTY_DURATION)).longValue();
    }

    public void setDuration(long duration) {
        put(PROPERTY_DURATION, duration);
    }

    public Date getDate() {
        return (Date)get(PROPERTY_DATE);
    }

    public void setDate(Date date) {
        put(PROPERTY_DATE, date);
    }

    /***
     * Gets all the properties stored in this TestCaseResult.
     *
     * @return the properties.
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "TestCaseResult{" +
                "testCase=" + testCase +
                ", properties=" + properties +
                '}';
    }
}
