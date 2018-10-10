package uk.gov.dvla.osl.commons.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.osl.commons.error.api.ErrorCodes;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class NotFoundExceptionMapper extends ExceptionMapperBase<NotFoundException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotFoundExceptionMapper.class);

    public NotFoundExceptionMapper(String applicationName) {
        super(applicationName);
    }

    @Override
    protected void logError(NotFoundException exception) {
        LOGGER.error("Not Found Exception: applicationName: {}, message: {}, cause: {}.",
                applicationName,
                exception.getMessage(),
                exception.getCause());
    }

    @Override
    protected List<ErrorInfo> toErrorList(NotFoundException exception) {
        List<ErrorInfo> errorInfoList = new ArrayList<>();
        errorInfoList.add(new ErrorInfo(ErrorCodes.RESOURCE_NOT_FOUND));
        return errorInfoList;
    }

    @Override
    protected Integer getStatus(NotFoundException exception) {
        return Response.Status.NOT_FOUND.getStatusCode();
    }

}
