package uk.gov.dvla.osl.commons.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.osl.commons.error.api.ErrorCodes;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


/**
 * Mapper which would handle exceptions that derive from IllegalArgumentException.
 */
public class IllegalArgumentExceptionMapper extends ExceptionMapperBase<IllegalArgumentException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IllegalArgumentExceptionMapper.class);

    public IllegalArgumentExceptionMapper(String applicationName) {
        super(applicationName);
    }

    @Override
    protected void logError(IllegalArgumentException exception) {
        LOGGER.error("Illegal Argument Exception: applicationName: {}, message: {}, cause: {}.",
                applicationName,
                exception.getMessage(),
                exception.getCause());
    }

    @Override
    protected List<ErrorInfo> toErrorList(IllegalArgumentException exception) {
        List<ErrorInfo> errorInfoList = new ArrayList<>();
        errorInfoList.add(new ErrorInfo(ErrorCodes.ILLEGAL_ARGUMENT_EXCEPTION));
        return errorInfoList;
    }

    @Override
    protected Integer getStatus(IllegalArgumentException exception) {
        return Response.Status.BAD_REQUEST.getStatusCode();
    }

}
