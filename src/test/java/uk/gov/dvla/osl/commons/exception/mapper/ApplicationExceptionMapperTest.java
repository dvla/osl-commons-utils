package uk.gov.dvla.osl.commons.exception.mapper;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.Test;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;
import uk.gov.dvla.osl.commons.error.api.response.ErrorsDTO;
import uk.gov.dvla.osl.commons.exception.ApplicationException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Simple test class for the ApplicationExceptionMapper, used for transforming our error info list
 * structures into standard exception response structures.
 * @since 1.0
 */
public class ApplicationExceptionMapperTest {

    /**
     * Simple title value for a particular contrived failure.
     */
    public static final String TEST_FAILURE_TITLE = "Test failure";

    /**
     * Simple detailed value for a particular contrived failure.
     */
    public static final String TEST_FAILURE_DETAIL = "Test failure detail";

    /**
     * Dummy value for the help link for a particular error condition.
     */
    public static final String TEST_HELP_LINK = "http://test.com";

    /**
     * Dummy code for a validation error condition.
     */
    public static final int TEST_VALIDATION_FAILURE_CODE = 101;

    /**
     * Title for a contrived validation error.
     */
    public static final String VALIDATION_FAILURE_TITLE = "Validation failed";

    /**
     * Detail message for a contrived validation error.
     */
    public static final String VALIDATION_FAILURE_DETAIL = "Validation failed on test field";

    /**
     * Trivial failure code for tests.
     */
    private  static final int TEST_FAILURE_CODE = 100;

    /**
     * Mapper instance under test.
     */
    private ApplicationExceptionMapper appExceptionMapper = new ApplicationExceptionMapper("test-app");
    private ConstraintViolationExceptionMapper constraintViolationExceptionMapper =
            new ConstraintViolationExceptionMapper("test-app", new TestViolationToErrorInfoMapper());

    /**
     * Test that a single error-containing exception is encoded correctly by the mapper.
     */
    @Test
    public void oneErrorIsEncodedCorrectlyConstraintViolationException() {

        Set<ConstraintViolation<String>> constraintViolations =
                new HashSet<ConstraintViolation<String>>();
        ConstraintViolation<String> violation =
                ConstraintViolationImpl.forParameterValidation("", null, "Invalid input data",
                        null, null, null, null,
                        PathImpl.createPathFromString("test"), null, null, null);
        constraintViolations.add(violation);
        final ConstraintViolationException ex = new ConstraintViolationException(constraintViolations);

        final Response resp = constraintViolationExceptionMapper.toResponse(ex);
        assertThat(resp.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        final ErrorsDTO errors = (ErrorsDTO) resp.getEntity();
        assertThat(errors.getErrors().size(), is(1));
        final ErrorInfo error = errors.getErrors().get(0);
        assertThat(error.getCode(), is(notNullValue()));
        assertThat(error.getStatus(), is(nullValue()));
        assertThat(error.getTitle(), is(notNullValue()));
        assertThat(error.getDetail(),  is(nullValue()));
        assertThat(error.getLink(),  is(nullValue()));
    }

    /**
     * Test that a single error-containing exception is encoded correctly by the mapper.
     */
    @Test
    public void oneErrorIsEncodedCorrectlyApplicationException() {

        final ApplicationException ex = testFailureException();

        final Response resp = appExceptionMapper.toResponse(ex);
        assertThat(resp.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        final ErrorsDTO errors = (ErrorsDTO) resp.getEntity();
        assertThat(errors.getErrors().size(), is(1));
        final ErrorInfo error = errors.getErrors().get(0);
        assertThat(error.getCode(), is(TEST_FAILURE_CODE));
        assertThat(error.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        assertThat(error.getTitle(), is(TEST_FAILURE_TITLE));
        assertThat(error.getDetail(), is(TEST_FAILURE_DETAIL));
        assertThat(error.getLink(), is(TEST_HELP_LINK));
    }

    /**
     * Validate that an exception containing multiple errors is encoded correctly by the mapper.
     */
    @Test
    public void multipleErrorsAreEncodedCorrectly() {

        final List<ErrorInfo> errorInfoList = new LinkedList<>();
        errorInfoList.add(testFailureErrorInfo());
        errorInfoList.add(validationErrorInfo());
        final ApplicationException ex = new ApplicationException(
                Response.Status.BAD_REQUEST.getStatusCode(),
                errorInfoList);
        final Response resp = appExceptionMapper.toResponse(ex);
        assertThat(resp.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));

        final ErrorsDTO errors = (ErrorsDTO) resp.getEntity();
        assertThat(errors.getErrors().size(), is(2));

        final ErrorInfo testFailure = errors.getErrors().get(0);
        assertThat(testFailure.getCode(), is(TEST_FAILURE_CODE));
        assertThat(testFailure.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        assertThat(testFailure.getTitle(), is(TEST_FAILURE_TITLE));
        assertThat(testFailure.getDetail(), is(TEST_FAILURE_DETAIL));
        assertThat(testFailure.getLink(), is(TEST_HELP_LINK));

        final ErrorInfo validationError = errors.getErrors().get(1);
        assertThat(validationError.getCode(), is(TEST_VALIDATION_FAILURE_CODE));
        assertThat(validationError.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(validationError.getTitle(), is(VALIDATION_FAILURE_TITLE));
        assertThat(validationError.getDetail(), is(VALIDATION_FAILURE_DETAIL));
        assertThat(validationError.getLink(), is(nullValue()));

    }

    /**
     * Generator for a contrived failure exception.
     * @return The exception.
     */
    private ApplicationException testFailureException() {
        return new ApplicationException(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                testFailureErrorInfo()
        );
    }

    /**
     * Generator for the failure error info.
     * @return ErrorInfo - the generated error info object
     */
    private ErrorInfo testFailureErrorInfo() {
        return new ErrorInfo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                TEST_FAILURE_CODE,
                TEST_FAILURE_TITLE,
                TEST_FAILURE_DETAIL,
                TEST_HELP_LINK);
    }

    /**
     * Generator for a validation error info.
     * @return ErrorInfo - the generated error info object
     */
    private ErrorInfo validationErrorInfo() {
        return new ErrorInfo(Response.Status.BAD_REQUEST.getStatusCode(),
                TEST_VALIDATION_FAILURE_CODE,
                VALIDATION_FAILURE_TITLE,
                VALIDATION_FAILURE_DETAIL);
    }
}
