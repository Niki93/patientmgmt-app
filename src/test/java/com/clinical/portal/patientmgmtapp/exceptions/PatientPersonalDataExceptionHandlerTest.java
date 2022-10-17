package com.clinical.portal.patientmgmtapp.exceptions;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class PatientPersonalDataExceptionHandlerTest {

    @InjectMocks
    private PatientPersonalDataExceptionHandler patientPersonalDataExceptionHandler=
            new PatientPersonalDataExceptionHandler();

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    private Validator validator;


    @BeforeMethod
    public void setUp() throws Exception{
        try{
            MockitoAnnotations.openMocks(this);
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void handleMethodArgumentNotValidException() throws NoSuchMethodException {
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "objectName");
        bindingResult.addError(new FieldError("objectName", "firstName", "message"));
        bindingResult.addError(new FieldError("objectName", "lastName", "message"));
        Method method = this.getClass().getMethod("setUp", (Class<?>[]) null);
        MethodParameter parameter = new MethodParameter(method, -1);
        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(parameter, bindingResult);

        ResponseEntity<PatientPersonalDataException> patientPersonalDataException =
                patientPersonalDataExceptionHandler.handleException(exception);

        assertEquals(patientPersonalDataException.getBody().getHttpStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(patientPersonalDataException.getBody().getDetails().size(),2);
    }

    @Test
    void handleBindingException() {
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "objectName");
        bindingResult.addError(new FieldError("objectName", "firstName", "message"));
        bindingResult.addError(new FieldError("objectName", "lastName", "message"));
        BindException exception =
                new BindException(bindingResult);

        ResponseEntity<PatientPersonalDataException> patientPersonalDataException =
                patientPersonalDataExceptionHandler.handleException(exception);
        assertEquals(patientPersonalDataException.getBody().getHttpStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(patientPersonalDataException.getBody().getDetails().size(),2);

    }

    @Test
    void handleConfigurationException(){
        ConfigurationException configurationException = new ConfigurationException(new ArrayList<>());
        ResponseEntity<String> patientPersonalDataException =
                patientPersonalDataExceptionHandler.handleException(configurationException);
        assertEquals(patientPersonalDataException.getStatusCode(),HttpStatus.BAD_REQUEST);

    }

    @Test
    void handleMappingException(){
        MappingException mappingException = new MappingException(new ArrayList<>());
        ResponseEntity<String> patientPersonalDataException =
                patientPersonalDataExceptionHandler.handleException(mappingException);
        assertEquals(patientPersonalDataException.getStatusCode(),HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleNullPointerException(){
        NullPointerException nullPointerException = new NullPointerException("NPE");
        ResponseEntity<String> patientPersonalDataException =
                patientPersonalDataExceptionHandler.handleException(nullPointerException);
        assertEquals(patientPersonalDataException.getStatusCode(),HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleInternalServerException(){
        RuntimeException runtimeException = new RuntimeException("Runtime exception");
        ResponseEntity<String> patientPersonalDataException =
                patientPersonalDataExceptionHandler.handleException(runtimeException);
        assertEquals(patientPersonalDataException.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}