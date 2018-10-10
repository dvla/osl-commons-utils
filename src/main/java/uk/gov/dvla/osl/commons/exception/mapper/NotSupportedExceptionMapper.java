package uk.gov.dvla.osl.commons.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.osl.commons.error.api.ErrorCodes;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper which would handle exceptions that derive from NotSupportedException.
 *
 */
public class NotSupportedExceptionMapper  extends ExceptionMapperBase<NotSupportedException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotSupportedExceptionMapper.class);

    public NotSupportedExceptionMapper(String applicationName) {
        super(applicationName);
    }

    @Override
    protected void logError(NotSupportedException exception) {
        LOGGER.error("Not Supported Media Type Exception: applicationName: {}, message: {}, cause: {}.",
                applicationName,
                exception.getMessage(),
                exception.getCause());
    }

    @Override
    protected List<ErrorInfo> toErrorList(NotSupportedException exception) {
        List<ErrorInfo> errorInfoList = new ArrayList<>();
        errorInfoList.add(new ErrorInfo(ErrorCodes.NOT_SUPPORTED_MEDIA_TYPE));
        return errorInfoList;
    }

    @Override
    protected Integer getStatus(NotSupportedException exception) {
        return Response.Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode();
    }

}
