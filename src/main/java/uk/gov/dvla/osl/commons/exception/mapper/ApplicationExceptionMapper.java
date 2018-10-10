package uk.gov.dvla.osl.commons.exception.mapper;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;
import uk.gov.dvla.osl.commons.exception.ApplicationException;

import java.util.List;


public class ApplicationExceptionMapper extends ExceptionMapperBase<ApplicationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionMapper.class);

    public ApplicationExceptionMapper(String applicationName) {
        super(applicationName);
    }

    @Override
    protected void logError(ApplicationException applicationException) {
        if (String.valueOf(applicationException.getStatus()).charAt(0) == '5') {
            for (ErrorInfo error : applicationException.getErrors()) {
                String message = getMessage(applicationException);
                if (message != null) {
                    LOGGER.error("Application Exception: applicationName: {}, code: {}, title: {}, causeMessage: {}",
                            applicationName,
                            error.getCode(),
                            error.getTitle(),
                            message,
                            applicationException);
                } else if (error.getDetail() != null) {
                    LOGGER.error("Application Exception: applicationName: {}, code: {}, title: {}, detail: {}",
                            applicationName,
                            error.getCode(),
                            error.getTitle(),
                            error.getDetail(),
                            applicationException);
                } else {
                    LOGGER.error("Application Exception: applicationName: {}, code: {}, title: {}",
                            applicationName,
                            error.getCode(),
                            error.getTitle(),
                            applicationException);
                }
            }
        } else {
            for (ErrorInfo error : applicationException.getErrors()) {
                String message = getMessage(applicationException);
                if (message != null) {
                    LOGGER.info("Application Exception: applicationName: {}, code: {}, title: {}, causeMessage: {}",
                            applicationName,
                            error.getCode(),
                            error.getTitle(),
                            message);
                } else if (error.getDetail() != null) {
                    LOGGER.info("Application Exception: applicationName: {}, code: {}, title: {}, detail: {}",
                            applicationName,
                            error.getCode(),
                            error.getTitle(),
                            error.getDetail());
                } else {
                    LOGGER.info("Application Exception: applicationName: {}, code: {}, title: {}",
                            applicationName,
                            error.getCode(),
                            error.getTitle());
                }
            }
        }
    }

    @Override
    protected List<ErrorInfo> toErrorList(ApplicationException applicationException) {
        return applicationException.getErrors();
    }

    @Override
    protected Integer getStatus(ApplicationException applicationException) {
        return applicationException.getStatus();
    }

    /**
     * The exception will usually wrap a HystrixException containing the underlying cause, so attempt to extract the
     * underlying cause message.  If not a HysrixException then use the actual exception cause message.
     */
     protected String getMessage(ApplicationException applicationException) {
        String message = null;

        if (applicationException.getCause() != null) {
            message = applicationException.getCause().getMessage();
            if(applicationException.getCause() instanceof HystrixRuntimeException
                && applicationException.getCause().getCause() != null
                        && applicationException.getCause().getCause().getMessage() != null) {
                        message = applicationException.getCause().getCause().getMessage();
            }
        }

       return message;
    }
}