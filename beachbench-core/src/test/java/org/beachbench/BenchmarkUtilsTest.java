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
public class BenchmarkUtilsTest {

    @Test
    public void test(){
        assertEquals("100", BenchmarkUtils.operationsPerSecondAsString(100, 1000));
        assertEquals(20.0, BenchmarkUtils.operationsPerSecondPerThread(10, 1000, 2),0);
        assertEquals("20", BenchmarkUtils.operationsPerSecondPerThreadAsString(10, 1000, 2));
        assertEquals(400.0, BenchmarkUtils.operationsPerSecond(100, 1000, 2),0);
        assertEquals("400", BenchmarkUtils.operationsPerSecondAsString(100, 1000, 2));
        assertEquals("100", BenchmarkUtils.format(100));
    }
}
