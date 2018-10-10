package uk.gov.dvla.osl.commons.error.api;

/**
 * Interface to represent an error condition which may occur in an application.
 * This is to be implemented by an enumeration within the application, the members of which
 * are used to identify predictable error conditions for feeding back to clients.
 * @since 1.0.0.
 */
public interface ErrorCondition {

    /**
     * Return an optional integer code for the error condition.
     * @return Integer - the error code for the error condition
     */
    Integer getCode();

    /**
     * Return the message associated with the error condition.
     * @return String - the message associated with the error condition
     */
    String getMessage();
}
