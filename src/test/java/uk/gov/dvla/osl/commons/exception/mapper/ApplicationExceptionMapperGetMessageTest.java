package uk.gov.dvla.osl.commons.exception.mapper;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Before;
import org.junit.Test;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;
import uk.gov.dvla.osl.commons.exception.ApplicationException;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test the get message implementation.
 */
public class ApplicationExceptionMapperGetMessageTest {

    private ApplicationExceptionMapper appExceptionMapper;
    private ErrorInfo errorInfo;
    private HystrixRuntimeException hystrixRuntimeException;

    private static final String TEST_GENERIC_MESSAGE = "Exception message";
    private static final String TEST_HYSTRIX_MESSAGE = "Hystrix Exception message";

    @Before
    public void setUp() {
        appExceptionMapper = new ApplicationExceptionMapper("test-app");
        errorInfo = new ErrorInfo(1, 1, "title", "detail");

        // The mocked Hystrix Exception cause is always set
        hystrixRuntimeException = mock(HystrixRuntimeException.class);
        when(hystrixRuntimeException.getMessage()).thenReturn(TEST_HYSTRIX_MESSAGE);
    }

    @Test
    public void testGetMessage_WrapsNoException() {
        String message = appExceptionMapper.getMessage(new ApplicationException(errorInfo));
        assertThat(message, nullValue());
    }

    @Test
    public void testGetMessage_WrapsGenericException_WhichHasGenericCause() {
        String message = appExceptionMapper.getMessage(new ApplicationException(errorInfo, new RuntimeException(TEST_GENERIC_MESSAGE)));
        assertThat(message, is(TEST_GENERIC_MESSAGE));
    }

    @Test
    public void testGetMessage_WrapsGenericException_WhichHasNoCause() {
        String message = appExceptionMapper.getMessage(new ApplicationException(errorInfo, new RuntimeException()));
        assertThat(message, nullValue());
    }

    @Test
    public void testGetMessage_WrapsHystrixException_WhichWrapsGenericException_WhichHasGenericCause() {
        when(hystrixRuntimeException.getCause()).thenReturn(new RuntimeException(TEST_GENERIC_MESSAGE));
        String message = appExceptionMapper.getMessage(new ApplicationException(errorInfo, hystrixRuntimeException));
        assertThat(message, is(TEST_GENERIC_MESSAGE));
    }

    @Test
    public void testGetMessage_WrapsHystrixException_WhichWrapsGenericException_WhichHasNoCause() {
        when(hystrixRuntimeException.getCause()).thenReturn(new RuntimeException());
        String message = appExceptionMapper.getMessage(new ApplicationException(errorInfo, hystrixRuntimeException));
        assertThat(message, is(TEST_HYSTRIX_MESSAGE));
    }

    @Test
    public void testGetMessage_WrapsHystrixException_WhichWrapsNoException() {
        String message = appExceptionMapper.getMessage(new ApplicationException(errorInfo, hystrixRuntimeException));
        assertThat(message, is(TEST_HYSTRIX_MESSAGE));
    }
}