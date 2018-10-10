package uk.gov.dvla.osl.commons.hystrix;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HystrixDefaultPropertiesTest {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(HystrixDefaultPropertiesTest.class);

        private final String FILE_NAME = "hystrix.properties";

        private final int BATCH_SIZE = 10;

        private final int WAIT = 10000;

        public static List<String> iterList= new ArrayList<>();


    /**
     * This test verifies hystrix command to run 10 concurrent calls, it fails due to version 1.5.2 and rectified in 1.5.9
     * @throws Exception
     */
     @Test
    public void allowMaximumSizeToDivergeFromCoreSizeErrorTest() throws Exception {

                ((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.INFO);

                ClassLoader classLoader = ClassLoader.getSystemClassLoader();

                System.setProperty("archaius.configurationSource.additionalUrls", "file://"
                        + classLoader.getResource(FILE_NAME).getFile());

                HystrixCommand.Setter setter = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("reproduce-the-bug"));

                final ExecutorService executor = Executors.newFixedThreadPool(BATCH_SIZE);

                final CircuitBreaker circuitBreaker = new CircuitBreaker(setter);

                int i = 0;

                while (i < BATCH_SIZE) {
                    logger.info("iterations"+i);
                    executor.submit(new CommandRunnable(circuitBreaker, "Issue Test " + i));
                    i++;
                }

                Thread.sleep(WAIT);

                assertThat(10,is(iterList.size()));

            }

        }

        class CommandRunnable implements Runnable {
            private static final Logger logger = (Logger) LoggerFactory.getLogger(CommandRunnable.class);
            private final CircuitBreaker circuitBreaker;
            private final String text;

            CommandRunnable(CircuitBreaker circuitBreaker, String text) {
                this.circuitBreaker = circuitBreaker;
                this.text = text;
            }

            @Override
            public void run() {
                try {
                    logger.info(circuitBreaker.returnText(text));
                    HystrixDefaultPropertiesTest.iterList.add(text);
                } catch (Exception e) {
                    logger.error(e.getCause().getMessage());
                }
            }
        }

        class CircuitBreaker {
            private final HystrixCommand.Setter setter;

            CircuitBreaker(HystrixCommand.Setter setter) {
                this.setter = setter;
            }

            String returnText(String text) {
                return new HystrixCommand<String>(setter) {
                    @Override
                    protected String run() throws Exception {
                        return text;
                    }
                }.execute();
            }
}
