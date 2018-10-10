package uk.gov.dvla.osl.commons.exception.mapper;


import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Before;
import org.junit.Test;
import uk.gov.dvla.osl.commons.error.api.ErrorCodes;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HystrixRuntimeExceptionMapperTest {

    private HystrixRuntimeExceptionMapper hystrixRuntimeExceptionMapper;

    private ErrorInfo errorInfo;

    private HystrixRuntimeException hystrixRuntimeException;

    private static final String TEST_HYSTRIX_MESSAGE = "Hystrix Exception message";

    @Before
    public void setUp() {
        hystrixRuntimeExceptionMapper = new HystrixRuntimeExceptionMapper("test-app");
        errorInfo = new ErrorInfo(1, 1, "title", "detail");
        hystrixRuntimeException = mock(HystrixRuntimeException.class);
        when(hystrixRuntimeException.getMessage()).thenReturn(TEST_HYSTRIX_MESSAGE);

    }

    @Test
    public void test_hystrixException_WhichHasBadRequestCause() {
        final Response resp = hystrixRuntimeExceptionMapper.toResponse(hystrixRuntimeException);
        assertThat(resp.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));

    }

    @Test
    public void test_hystrixException_WhichHasBadRequestCause_ShouldSaveInternalServerError() {
        final Response resp = hystrixRuntimeExceptionMapper.toResponse(hystrixRuntimeException);
        List<ErrorInfo> errorList = hystrixRuntimeExceptionMapper.toErrorList(hystrixRuntimeException);
        assertThat(errorList.get(0).getTitle(), is(ErrorCodes.HYSTRIX_CMD_FAILURE.getMessage()));

    }

    @Test
    public void test_hystrixException_WhichWrapsRuntimeException_ShouldSaveInternalServerErrore() {
        when(hystrixRuntimeException.getCause()).thenReturn(new RuntimeException());
        List<ErrorInfo> errorList = hystrixRuntimeExceptionMapper.toErrorList(hystrixRuntimeException);
        assertThat(errorList.get(0).getTitle(), is(ErrorCodes.HYSTRIX_CMD_FAILURE.getMessage()));

    }

    @Test
    public void test_hystrixException_WhichWrapsGenericException_ShouldHasNoCause() {
        when(hystrixRuntimeException.getCause()).thenReturn(new Exception());
        List<ErrorInfo> errorList = hystrixRuntimeExceptionMapper.toErrorList(hystrixRuntimeException);
        assertThat(errorList.get(0).getTitle(), is(ErrorCodes.HYSTRIX_CMD_FAILURE.getMessage()));

    }


}
