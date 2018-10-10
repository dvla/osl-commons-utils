package uk.gov.dvla.osl.commons.exception.mapper;


import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.validation.ConstraintViolation;

public class TestViolationToErrorInfoMapper implements ViolationToErrorInfoMapper {

    public ErrorInfo violationToErrorInfo(ConstraintViolation constraintViolation) {
        return new ErrorInfo(null, 100, constraintViolation.getMessage(), null);
    }

}
