package uk.gov.dvla.osl.commons.exception.mapper;

import org.glassfish.jersey.server.ParamException;
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
public class PathParamExceptionMapper extends ExceptionMapperBase<ParamException.PathParamException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathParamExceptionMapper.class);

    public PathParamExceptionMapper(String applicationName) {
        super(applicationName);
    }

    @Override
    protected void logError(ParamException.PathParamException exception) {
        LOGGER.error("Path Param Exception: applicationName: {}, message: {}, cause: {}.",
                applicationName,
                exception.getMessage(),
                exception.getCause());
    }

    @Override
    protected List<ErrorInfo> toErrorList(ParamException.PathParamException exception) {
        List<ErrorInfo> errorInfoList = new ArrayList<>();
        errorInfoList.add(new ErrorInfo(ErrorCodes.PATH_PARAM_EXCEPTION));
        return errorInfoList;
    }

    @Override
    protected Integer getStatus(ParamException.PathParamException exception) {
        return Response.Status.BAD_REQUEST.getStatusCode();
    }

}
