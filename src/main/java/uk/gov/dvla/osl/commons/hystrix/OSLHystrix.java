package uk.gov.dvla.osl.commons.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;

/**
 * Utility class to help with constructing and executing Hystrix Commands
 */
public class OSLHystrix {

    private static final Logger LOG = LoggerFactory.getLogger(OSLHystrix.class);

    public interface SupplierWithException<T> {
        T get() throws Exception;
    }

    public interface ActionWithException {
        void perform() throws Exception;
    }

    public static class CommandBuilder {

        HystrixCommand.Setter setter;

        public <T> T get(SupplierWithException<T> supplier) {
            final Map<String,String> contextMap = MDC.getCopyOfContextMap();

            return new HystrixCommand<T>(setter) {
                @Override
                protected T run() throws Exception {
                    if (contextMap != null){
                        MDC.setContextMap(contextMap);
                    }
                    try {
                        return supplier.get();
                    } catch ( Exception exc ) {
                        LOG.error("Failed to get via Hystrix: {}",exc);
                        throw exc;
                    }
                }
            }.execute();
        }

        public void execute(ActionWithException action) {
            final Map<String,String> contextMap = MDC.getCopyOfContextMap();

            new HystrixCommand<Void>(setter) {
                @Override
                protected Void run() throws Exception {
                    if (contextMap != null) {
                        MDC.setContextMap(contextMap);
                    }
                    try {
                        action.perform();
                        return null;
                    } catch ( Exception exc ) {
                        LOG.error("Failed to perform via Hystrix: {}",exc);
                        throw exc;
                    }
                }
            }.execute();
        }

        public CommandBuilder withSetter(HystrixCommand.Setter setter) {
            this.setter = setter;
            return this;
        }
    }

    public static CommandBuilder hystrixCommandBuilder() {
        return new CommandBuilder();
    }


}
