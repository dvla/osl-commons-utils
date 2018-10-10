package uk.gov.dvla.osl.commons.exception;

import org.junit.Test;
import uk.gov.dvla.osl.commons.error.api.ErrorCodes;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test for the application exception.
 */
public class ApplicationExceptionTest {

    /**
     * Test that the detail text on the error info is set correctly.
     */
    @Test
    public void testApplicationException_ErrorInfo_Detail() {

        ApplicationException e = new ApplicationException(400, new ErrorInfo(ErrorCodes.BAD_REQUEST));
        assertThat(e.getErrors().get(0), is(notNullValue()));
        assertThat(e.getErrors().get(0).getDetail(), is(nullValue()));

        String exceptionText = "Exception text";
        e = new ApplicationException(400, new ErrorInfo(ErrorCodes.BAD_REQUEST, exceptionText));
        assertThat(e.getErrors().get(0).getDetail(), is(exceptionText));
    }
}