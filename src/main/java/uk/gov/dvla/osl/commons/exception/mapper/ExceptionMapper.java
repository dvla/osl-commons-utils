package uk.gov.dvla.osl.commons.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.osl.commons.error.api.ErrorCodes;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


public class ExceptionMapper extends ExceptionMapperBase<Exception> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapper.class);

    public ExceptionMapper(String applicationName) {
        super(applicationName);
    }

    @Override
    protected void logError(Exception exception) {
        LOGGER.error("Exception: applicationName: {}, message: {}, cause: {}.",
                applicationName,
                exception.getMessage(),
                exception.getCause());
        LOGGER.error("",exception);
    }

    @Override
    protected List<ErrorInfo> toErrorList(Exception exception) {
        List<ErrorInfo> errorInfoList = new ArrayList<>();
        errorInfoList.add(new ErrorInfo(ErrorCodes.UNEXPECTED_EXCEPTION));
        return errorInfoList;
    }

    @Override
    protected Integer getStatus(Exception exception) {
        return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
    }

}
