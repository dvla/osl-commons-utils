package uk.gov.dvla.osl.commons.exception.mapper;

import uk.gov.dvla.osl.commons.error.api.ErrorCodes;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;
import uk.gov.dvla.osl.commons.exception.mapper.ViolationToErrorInfoMapper;

import javax.validation.ConstraintViolation;


/**
 *  error mapper.
 */
public class ConstraintViolationToErrorInfoMapper implements ViolationToErrorInfoMapper {

    /**
     * validation error mapping.
     * @param constraintViolation the violation
     * @return error info.
     */
    public ErrorInfo violationToErrorInfo(ConstraintViolation constraintViolation) {

        if (constraintViolation.getInvalidValue() == null) {
            return new ErrorInfo(null, ErrorCodes.CONSTRAINT_VIOLATION_MANDATORY_REQUEST_VALUE.getCode(),
                    ErrorCodes.CONSTRAINT_VIOLATION_MANDATORY_REQUEST_VALUE.getMessage()
                            + " '"
                            + constraintViolation.getPropertyPath().toString()
                            + "' "
                            + constraintViolation.getMessage(),
                    null);
        } else {
            return new ErrorInfo(null, ErrorCodes.CONSTRAINT_VIOLATION.getCode(),
                    ErrorCodes.CONSTRAINT_VIOLATION.getMessage()
                            + " '"
                            + constraintViolation.getPropertyPath().toString()
                            + "' "
                            + constraintViolation.getMessage(),
                    null);
        }
    }
}
