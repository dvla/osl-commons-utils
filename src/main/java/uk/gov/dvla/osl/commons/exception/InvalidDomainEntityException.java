package uk.gov.dvla.osl.commons.exception;


import uk.gov.dvla.osl.commons.error.api.ErrorCondition;

public class InvalidDomainEntityException extends Exception {

    private ErrorCondition errorCondition;

    public InvalidDomainEntityException(ErrorCondition errorCondition){
        this.errorCondition = errorCondition;
    }

    public ErrorCondition getErrorCondition() {
        return errorCondition;
    }
}