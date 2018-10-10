package uk.gov.dvla.osl.commons.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.osl.commons.error.api.response.ErrorInfo;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ConstraintViolationExceptionMapper extends ExceptionMapperBase<ConstraintViolationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);

    private ViolationToErrorInfoMapper violationMapper;

    public ConstraintViolationExceptionMapper(String applicationName, ViolationToErrorInfoMapper violationMapper) {
        super(applicationName);
        this.violationMapper = violationMapper;
    }

    @Override
    protected void logError(ConstraintViolationException constraintViolationException) {
        for (ConstraintViolation cv : constraintViolationException.getConstraintViolations()) {
            LOGGER.error("Constraint Violation Exception: applicationName: {}, property: {}, invalidValue:{}, constraintMessage: {}.",
                    applicationName,
                    cv.getPropertyPath().toString(),
                    cv.getInvalidValue(),
                    cv.getMessage());
        }
    }

    @Override
    protected List<ErrorInfo> toErrorList(ConstraintViolationException constraintViolationException) {
        Set<ErrorInfo> errorInfoList = new HashSet<>();
        for (ConstraintViolation cv : constraintViolationException.getConstraintViolations()) {
            errorInfoList.add(violationMapper.violationToErrorInfo(cv));
        }
        return new ArrayList<>(errorInfoList);
    }

    @Override
    protected Integer getStatus(ConstraintViolationException constraintViolationException) {
        return Response.Status.BAD_REQUEST.getStatusCode();
    }

}
