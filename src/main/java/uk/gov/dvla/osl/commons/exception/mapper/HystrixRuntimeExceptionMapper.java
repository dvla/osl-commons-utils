package uk.gov.dvla.osl.commons.exception.mapper;


import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.osl.commons.error.api.ErrorCodes;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class HystrixRuntimeExceptionMapper extends ExceptionMapperBase<HystrixRuntimeException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixRuntimeExceptionMapper.class);

    public HystrixRuntimeExceptionMapper(String applicationName) {
        super(applicationName);
    }

    @Override
    protected void logError(HystrixRuntimeException exception) {
        LOGGER.error("Hystrix Runtime Exception: applicationName: {}, message: {}, cause: {}.",
                applicationName,
                exception.getMessage(),
                exception.getCause());

    }

    @Override
    protected List<ErrorInfo> toErrorList(HystrixRuntimeException exception) {
            List<ErrorInfo> errorInfoList = new ArrayList<>();
            errorInfoList.add(new ErrorInfo(ErrorCodes.HYSTRIX_CMD_FAILURE));

        return errorInfoList;
    }

    @Override
    protected Integer getStatus(HystrixRuntimeException exception) {
        return Response.Status.BAD_REQUEST.getStatusCode();
    }
}
