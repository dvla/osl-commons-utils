package uk.gov.dvla.osl.commons.exception.mapper;


import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;
import uk.gov.dvla.osl.commons.error.api.response.ErrorsDTO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


public abstract class ExceptionMapperBase<T extends Exception> implements javax.ws.rs.ext.ExceptionMapper<T> {

    protected String applicationName;

    protected ExceptionMapperBase(String applicationName) {
        this.applicationName = applicationName;
    }

    public Response toResponse(final T exception) {

        logError(exception);
        final List<ErrorInfo> errors = toErrorList(exception);

        return Response.status(getStatus(exception))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorsDTO(errors))
                .build();
    }

    protected abstract void logError(T exception);

    protected abstract List<ErrorInfo> toErrorList(T exception);

    protected abstract Integer getStatus(T exception);

}
