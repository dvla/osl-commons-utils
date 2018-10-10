package uk.gov.dvla.osl.commons.exception.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.osl.commons.error.api.ErrorCodes;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


/**
 * Mapper which would handle exceptions that derive from JsonProcessingException. Examples are:
 * JsonParseException, JsonMappingException and UnrecognizedPropertyException.
 */
public class JsonProcessingExceptionMapper extends ExceptionMapperBase<JsonProcessingException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonProcessingExceptionMapper.class);

    public JsonProcessingExceptionMapper(String applicationName) {
        super(applicationName);
    }

    @Override
    protected void logError(JsonProcessingException exception) {
        LOGGER.error("Json Processing Exception: applicationName: {}, message: {}, cause: {}.",
                applicationName,
                exception.getMessage(),
                exception.getCause());
    }

    @Override
    protected List<ErrorInfo> toErrorList(JsonProcessingException exception) {
        List<ErrorInfo> errorInfoList = new ArrayList<>();
        errorInfoList.add(new ErrorInfo(ErrorCodes.JSON_PROCESSING_EXCEPTION, exception.getOriginalMessage()));
        return errorInfoList;
    }

    @Override
    protected Integer getStatus(JsonProcessingException exception) {
        return Response.Status.BAD_REQUEST.getStatusCode();
    }

}
