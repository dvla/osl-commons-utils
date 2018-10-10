package uk.gov.dvla.osl.commons.error.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Class to represent a standard response body structure for an error condition encountered within
 * the processing of the VED calculation. This in turn wraps a list of error objects, for
 * consumption by the caller
 *
 * @author Jeremy Prime
 * @since 1.0.0
 */
public class ErrorsDTO {

    /**
     * Errors contained within the response object.
     */
    private final List<ErrorInfo> errors;

    /**
     * Constructor based on errors provided.
     *
     * @param errors The error information to be contained within the response object
     */
    public ErrorsDTO(@JsonProperty("errors") final List<ErrorInfo> errors) {
        this.errors = errors;
    }

    /**
     * Return the contained errors (ensure our list remains immutable by cloning).
     *
     * @return The list of errors contained within the response object
     */
    public List<ErrorInfo> getErrors() {
        return new LinkedList<>(errors);
    }
}
