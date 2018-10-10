package uk.gov.dvla.osl.commons.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.osl.commons.error.api.ErrorCodes;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


public class BadRequestExceptionMapper extends ExceptionMapperBase<BadRequestException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BadRequestExceptionMapper.class);

    public BadRequestExceptionMapper(String applicationName) {
        super(applicationName);
    }

    @Override
    protected void logError(BadRequestException exception) {
        LOGGER.error("Bad Request Exception: applicationName: {}, message: {}, cause: {}.",
                applicationName,
                exception.getMessage(),
                exception.getCause());
    }

    @Override
    protected List<ErrorInfo> toErrorList(BadRequestException exception) {
        List<ErrorInfo> errorInfoList = new ArrayList<>();
        errorInfoList.add(new ErrorInfo(ErrorCodes.BAD_REQUEST));
        return errorInfoList;
    }

    @Override
    protected Integer getStatus(BadRequestException exception) {
        return Response.Status.BAD_REQUEST.getStatusCode();
    }

}
