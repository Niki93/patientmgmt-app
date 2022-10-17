package com.clinical.portal.patientmgmtapp.exceptions;

import com.clinical.portal.patientmgmtapp.controller.PatientController;
import com.clinical.portal.patientmgmtapp.messages.response.Detail;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = {PatientController.class})
public class PatientPersonalDataExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(PatientPersonalDataExceptionHandler.class);
    private static final String DATA_MAP_ERRORS = "An error occurred while mapping the data";

    @ExceptionHandler(value={Exception.class})
    public ResponseEntity handleException(Exception exception){
        log.error("An exception is being thrown {}", exception);
        if(exception.getClass().equals(MethodArgumentNotValidException.class)){
            return handleMethodArgumentNotValidException((MethodArgumentNotValidException)exception);
        }else if(exception.getClass().equals(BindException.class)){
            return handleBindException((BindException) exception);
        }else if((exception.getClass().equals(ConfigurationException.class)) ||
                (exception.getClass().equals(MappingException.class))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DATA_MAP_ERRORS);
        }else if(exception.getClass().equals(NullPointerException.class)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An Error occurred, please check.");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
    }

    private ResponseEntity<PatientPersonalDataException>
    handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return getPatientPersonalDataExceptionResponseEntity(bindingResult);
    }

    private ResponseEntity<PatientPersonalDataException>
    handleBindException(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return getPatientPersonalDataExceptionResponseEntity(bindingResult);
    }

    private ResponseEntity<PatientPersonalDataException> getPatientPersonalDataExceptionResponseEntity(BindingResult bindingResult){
        PatientPersonalDataException patientPersonalDataException = new PatientPersonalDataException();
        patientPersonalDataException.setHttpStatus(HttpStatus.BAD_REQUEST);
        patientPersonalDataException.setDetails(getFieldErrorDetailList(bindingResult));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(patientPersonalDataException);
    }
    private List<Detail> getFieldErrorDetailList(BindingResult bindingResult){
        List<Detail> detailList = new ArrayList<>();
        for (FieldError constraintViolation : bindingResult.getFieldErrors()) {
            Detail detail = new Detail(constraintViolation.getField(),constraintViolation.getDefaultMessage());
            detailList.add(detail);
        }
        return detailList;
    }

}
