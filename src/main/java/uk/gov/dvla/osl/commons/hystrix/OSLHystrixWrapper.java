package uk.gov.dvla.osl.commons.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.slf4j.MDC;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import static com.netflix.hystrix.HystrixCommand.Setter.withGroupKey;
import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;
import static uk.gov.dvla.osl.commons.hystrix.OSLHystrix.hystrixCommandBuilder;

/**
 * Wrap an object with an interface into a set of calls that go through Hystrix,
 * e.g. useful for Dao and Retrofit clients to just create an interface and Hystrix-ise it
 * rather than create loads of commands.
 * Pass the MDC from the spawning thread into the invoked Hystrix thread to promulgate
 * the CorrelationId.
 * @author luke
 */
public class OSLHystrixWrapper {

    public static <T> T wrapWithHystrix(Object obj, Class<T> intf ) {
        return wrapWithHystrix(obj,intf,withGroupKey(asKey(obj.getClass().toString())));
    }

    public static <T> T wrapWithDisabledHystrixTimeOut(Object obj, Class<T> intf) {
        HystrixCommand.Setter setter = withGroupKey(asKey(obj.getClass().toString()));
        setter.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                .withExecutionTimeoutEnabled(false));
        return wrapWithHystrix(obj,intf,setter);
    }
    public static <T> T wrapWithHystrix(Object obj, Class<T> intf, HystrixCommand.Setter setter ) {

        if ( ! intf.isAssignableFrom(obj.getClass())  ) {
            throw new IllegalArgumentException("Object is not of the supplied interface");
        }

        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[] { intf }, new Handler(obj,setter));
    }


    private static class Handler implements InvocationHandler {

        private final Object target;
        private final HystrixCommand.Setter setter;

        public Handler(Object target, HystrixCommand.Setter setter) {
            this.target = target;
            this.setter = setter;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            final Map<String,String> contextMap = MDC.getCopyOfContextMap();

            return hystrixCommandBuilder()
                    .withSetter(setter)
                    .get( () -> {
                        if (contextMap != null) {
                            MDC.setContextMap(contextMap);
                        }
                        return method.invoke(target,args);
                    } );
        }
    }


}
