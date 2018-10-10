package uk.gov.dvla.osl.commons.error.api;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum ErrorCodes implements ErrorCondition {

    HYSTRIX_CMD_FAILURE(10001, "Internal server error"),
    UNSUCCESSFUL_RESPONSE(10002, "Unsuccessful response"),
    MISSING_CORRELATION_ID(10003, "X-Correlation-Id is a mandatory HTTP Header param"),
    UNEXPECTED_EXCEPTION(10004, "Unexpected exception"),
    CONSTRAINT_VIOLATION(10005, "Invalid request value"),
    BAD_REQUEST(10006, "Bad request"),
    JSON_PROCESSING_EXCEPTION(10007, "Invalid JSON"),
    CONSTRAINT_VIOLATION_MANDATORY_REQUEST_VALUE(10008, "Mandatory request value"),
    NOT_SUPPORTED_MEDIA_TYPE(10009,"Invalid request type"),
    RESOURCE_NOT_FOUND(10148004, "The specified resource was not found"),
    PATH_PARAM_EXCEPTION(10011, "The path parameter is invalid"),
    ILLEGAL_ARGUMENT_EXCEPTION(10012, "An argument is invalid");

    /**
     * The code for the error condition.
     */
    @Getter
    private Integer code;


    /**
     * The error message associated with the error condition.
     */
    @Getter
    private String message;

}
