package com.clinical.portal.patientmgmtapp.controller;

import com.clinical.portal.patientmgmtapp.DataBaseClass;
import com.clinical.portal.patientmgmtapp.messages.NameSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.YearSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.response.PatientDataResponse;
import com.clinical.portal.patientmgmtapp.messages.response.PatientListResponse;
import com.clinical.portal.patientmgmtapp.services.PatientService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class PatientControllerTest extends DataBaseClass{

    private PatientController patientController;

    @Mock
    private PatientService patientService;

    @BeforeMethod
    public void setUp() throws Exception{
        try{
            MockitoAnnotations.openMocks(this);
            patientController = new PatientController(patientService);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void savePatientData(){
        when(patientService.savePatientPersonalData(any())).thenReturn(getPatientDataResponse());
        ResponseEntity<PatientDataResponse> responseEntity =
                patientController.savePatientData(getPatientPersonalDataDto());
        verify(patientService,times(1)).savePatientPersonalData(any());
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.CREATED);
    }

    @Test
    void retrievePatientData() {
        when(patientService.retrievePatientPersonalDataByNames(any())).thenReturn(getPatientListResponseByNames());
        ResponseEntity<PatientListResponse> responseEntity =
                patientController.retrievePatientData(new NameSearchQueryParameters("Em","En"));
        verify(patientService,times(1)).retrievePatientPersonalDataByNames(any());
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        assertEquals(responseEntity.getBody().getPatients().size(),1);

    }

    @Test
    void retrievePatientCountByYears() {
        when(patientService.retrievePatientPersonalDataByYearsBorn(any())).thenReturn(getPatientListResponseByYears());
        ResponseEntity<PatientListResponse> responseEntity =
                patientController.retrievePatientCountByYears(new YearSearchQueryParameters("2020","2022"));
        verify(patientService,times(1)).retrievePatientPersonalDataByYearsBorn(any());
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        assertEquals(responseEntity.getBody().getPatientCntByYrsResult().size(),2);

    }
}