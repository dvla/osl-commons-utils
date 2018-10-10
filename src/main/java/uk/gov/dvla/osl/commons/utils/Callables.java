package uk.gov.dvla.osl.commons.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Callables {

    public static <T> T execute(ExecutorService executor, int tries, int retryIntervalSecs, int timeoutSecs, Callable<T> task) throws Exception {

        Exception lastThrown = null;
        for (int i = 1; tries <= 0 || i <= tries; i++) {
            try {
                Future<T> future = executor.submit(task);
                if ( timeoutSecs > 0 ) return future.get(timeoutSecs, TimeUnit.SECONDS);
                else return future.get();
            } catch (TimeoutException ex) {
                lastThrown = ex;
            } catch (ExecutionException ex) {
                lastThrown = (Exception) ex.getCause();
            }
            Thread.sleep(TimeUnit.SECONDS.toMillis(retryIntervalSecs));
        }
        if (lastThrown == null) {
            lastThrown = new TimeoutException("Reached max tries without being caused by some exception. " + task.getClass());
        }
        throw lastThrown;
    }
}

