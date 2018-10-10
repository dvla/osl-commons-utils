package uk.gov.dvla.osl.commons.error.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.dvla.osl.commons.error.api.ErrorCondition;

import java.util.Objects;

/**
 * Class representing a single error, to be returned as part of a list in the body of an error
 * response from the API. Where we are doing validations, for example, we will return one of these
 * for each validation failure and compile into a more general exception containing the sum total
 * of all identified failures.
 *
 * @since 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorInfo {

    /**
    The HTTP status code which this error would represent if it were the only error identified, e.g.
     validation failure would correspond to a 400.
     */
    private final Integer status;

    /**
     * Our own code for the error in question, to enable, for example, quick access via a help
     * website.
     */
    private final Integer code;

    /**
     * Summary of the error, not necessarily offering detail of the cause.
     */
    private final String title;

    /**
     * Detail error message, for example containing information about which field failed validation.
     */
    private final String detail;

    /**
     * A url which can be consulted for more help on the specific error, to assist whoever is
     * responsible for making the call which led to the error.
     */
    private final String link;

    /**
     * No args constructor
     */
    public ErrorInfo() {
        this.detail = null;
        this.title = null;
        this.status = null;
        this.code = null;
        this.link = null;
    }

    /**
     * Constructor based on a status and the error condition enumeration to provide code and message.
     * @param status the HTTP status to which this error alone would correspond
     * @param errorCondition the error condition which this error represents.
     */
    public ErrorInfo(final int status, final ErrorCondition errorCondition) {
        this(status, errorCondition.getCode(), errorCondition.getMessage(), null);
    }

    /**
     * Constructor based on a status, error condition enumeration and detail to provide code and message.
     * @param status the HTTP status to which this error alone would correspond
     * @param errorCondition the error condition which this error represents
     * @param detail the error detail
     */
    public ErrorInfo(final int status, final ErrorCondition errorCondition, final String detail) {
        this(status, errorCondition.getCode(), errorCondition.getMessage(), detail);
    }

    /**
     * Constructor based on error condition enumeration to provide code and message.
     * @param errorCondition the error condition which this error represents.
     */
    public ErrorInfo(final ErrorCondition errorCondition) {
        this(null, errorCondition.getCode(), errorCondition.getMessage(), null);
    }

    /**
     * Constructor based on error condition enumeration to provide code and message.
     * @param errorCondition the error condition which this error represents.
     * @param detail the error detail
     */
    public ErrorInfo(final ErrorCondition errorCondition, final String detail) {
        this(null, errorCondition.getCode(), errorCondition.getMessage(), detail);
    }

    /**
     * Constructor excluding optional member for help link.
     * @param status the HTTP status code to which this error alone would correspond
     * @param code error code to enable lookup for help, for example
     * @param title summary information about the error
     * @param detail detailed information about the error
     */
    public ErrorInfo(final Integer status, final Integer code, final String title,
                     final String detail) {
        this(status, code, title, detail, null);
    }

    /**
     * Constructor including optional member for help link.
     * @param status the HTTP status code to which this error alone would correspond
     * @param code error code to enable lookup for help, for example
     * @param title summary information about the error
     * @param detail detailed information about the error
     * @param link link for further information about the error (optional)
     */
    public ErrorInfo(@JsonProperty("status") final Integer status,
                     @JsonProperty("code") final Integer code,
                     @JsonProperty("title") final String title,
                     @JsonProperty("detail") final String detail,
                     @JsonProperty("link") final String link) {

        Objects.requireNonNull(code);
        Objects.requireNonNull(title);

        this.status = status;
        this.code = code;
        this.title = title;
        this.detail = detail;
        this.link = link;
    }

    /**
     * Return the equivalent HTTP status code.
     * @return integer representing the HTTP status code
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Return the error code for external lookup.
     * @return integer representing the error code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Return the error title, or summary.
     * @return title, or summary
     */
    public String getTitle() {
        return title;
    }


    /**
     * Return the error detail information.
     * @return detailed error information
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Return the url for a link to further information about this error.
     * @return help url
     */
    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ErrorInfo errorInfo = (ErrorInfo) obj;
        return Objects.equals(status, errorInfo.status) &&
                Objects.equals(code, errorInfo.code) &&
                Objects.equals(title, errorInfo.title) &&
                Objects.equals(detail, errorInfo.detail) &&
                Objects.equals(link, errorInfo.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, code, title, detail, link);
    }
}
