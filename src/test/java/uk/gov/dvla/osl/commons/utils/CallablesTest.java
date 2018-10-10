package uk.gov.dvla.osl.commons.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class CallablesTest {

    private static Logger LOG = LoggerFactory.getLogger(CallablesTest.class);

    ExecutorService executorService = Executors.newSingleThreadExecutor();


    private static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            LOG.debug("",e);
        }
    }

    @Test
    public void testNoTimeout() throws Exception {
        Callables.execute(executorService, 1, 0, 10, Executors.callable(() -> sleep(1)));
    }

    @Test(expected = TimeoutException.class)
    public void testTimeout() throws Exception {
        Callables.execute(executorService, 1, 0, 1, Executors.callable(() -> sleep(10000)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRetries() throws Exception {

        Callable<?> cb = mock(Callable.class);
        when(cb.call()).thenThrow(new IllegalArgumentException());

        try {
            Callables.execute(executorService, 3, 1, 1, cb);
        }
        finally {
            verify(cb, times(3)).call();
        }
    }
}