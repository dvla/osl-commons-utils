package uk.gov.dvla.osl.commons.exception;


import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;


/**
 * Exception class to represent passing back a list of failures to the client under an error
 * condition (for example all validation failures experienced for a particular request).
 *
 * @author Jeremy Prime
 * @since 1.0.0
 */
public class ApplicationException extends RuntimeException {

    private final Integer status;

    /**
     * The errors identified during request processing.
     */
    private final List<ErrorInfo> errors;


    /**
     * Constructor where only one error has occurred or been identified - this is to avoid client
     * code having to construct a list when we can internalise it comfortably.
     *
     * @param error The information relating to the error
     */
    public ApplicationException(final ErrorInfo error) {
        this(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), error);
    }

    /**
     * Constructor where only one error has occurred or been identified - this is to avoid client
     * code having to construct a list when we can internalise it comfortably.
     *
     * @param error The information relating to the error
     * @param cause The cause of the exception
     */
    public ApplicationException(final ErrorInfo error, final Throwable cause) {
        this(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), error, cause);
    }

    /**
     * Constructor where only one error has occurred or been identified - this is to avoid client
     * code having to construct a list when we can internalise it comfortably.
     *
     * @param status The http status to which the error corresponds
     * @param error The information relating to the error
     */
    public ApplicationException(final Integer status, final ErrorInfo error) {
        this.status = status;
        this.errors = new LinkedList<>();
        errors.add(error);
    }

    /**
     * Constructor where only one error has occurred or been identified - this is to avoid client
     * code having to construct a list when we can internalise it comfortably.
     *
     * @param status The http status to which the error corresponds
     * @param error The information relating to the error
     * @param cause The cause of the exception
     */
    public ApplicationException(final Integer status, final ErrorInfo error, final Throwable cause) {
        super(cause);
        this.status = status;
        this.errors = new LinkedList<>();
        errors.add(error);
    }

    /**
     * Standard constructor where we already have a list of errors.
     *
     * @param errors The list of errors which we have identified during processing
     */
    public ApplicationException(final List<ErrorInfo> errors) {
        this(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), errors);
    }

    /**
     * Standard constructor where we already have a list of errors.
     *
     * @param errors The list of errors which we have identified during processing
     * @param cause The cause of the exception
     */
    public ApplicationException(final List<ErrorInfo> errors, final Throwable cause) {
        this(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), errors, cause);
    }

    /**
     * Standard constructor where we already have a list of errors.
     *
     * @param status The overall http status to which we are mapping the list of errors
     * @param errors The list of errors which we have identified during processing
     */
    public ApplicationException(final Integer status, final List<ErrorInfo> errors) {
        this.status = status;
        this.errors = errors;
    }

    /**
     * Standard constructor where we already have a list of errors.
     *
     * @param status The overall http status to which we are mapping the list of errors
     * @param errors The list of errors which we have identified during processing
     * @param cause The cause of the exception
     */
    public ApplicationException(final Integer status, final List<ErrorInfo> errors, final Throwable cause) {
        super(cause);
        this.status = status;
        this.errors = errors;
    }

    /**
     * Return an immutable list of the errors provided to this exception. To be used for
     * generating the response content.
     *
     * @return list of errors
     */
    public List<ErrorInfo> getErrors() {
        return new LinkedList<>(errors);
    }

    public Integer getStatus() {
        return this.status;
    }

}
