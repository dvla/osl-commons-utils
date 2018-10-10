package uk.gov.dvla.osl.commons.exception.mapper;


import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.validation.ConstraintViolation;


public interface ViolationToErrorInfoMapper {

    ErrorInfo violationToErrorInfo(ConstraintViolation constraintViolation);

}
