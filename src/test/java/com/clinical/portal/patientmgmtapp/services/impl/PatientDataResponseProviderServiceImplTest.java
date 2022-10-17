package com.clinical.portal.patientmgmtapp.services.impl;

import com.clinical.portal.patientmgmtapp.DataBaseClass;
import com.clinical.portal.patientmgmtapp.messages.NameSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.YearSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.response.PatientDataResponse;
import com.clinical.portal.patientmgmtapp.messages.response.PatientListResponse;
import com.clinical.portal.patientmgmtapp.services.PatientDataResponseProviderService;
import com.clinical.portal.patientmgmtapp.services.PatientPersonalDataEntityLookup;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class PatientDataResponseProviderServiceImplTest extends DataBaseClass {

    private PatientDataResponseProviderService patientDataResponseProviderService;

    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private PatientPersonalDataEntityLookup patientPersonalDataEntityLookup;

    @BeforeMethod
    public void setUp() throws Exception{
        try{
            MockitoAnnotations.openMocks(this);
            patientDataResponseProviderService = new PatientDataResponseProviderServiceImpl(
                    patientPersonalDataEntityLookup,modelMapper);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void savePatientPersonalData_CreatedTrue() {
        when(patientPersonalDataEntityLookup.findIfPatientDataExists(any())).thenReturn(Optional.empty());
        when(patientPersonalDataEntityLookup.savePatientPersonalData(any())).thenReturn(getPatientPersonalData());
        PatientDataResponse patientDataResponse =
                patientDataResponseProviderService.savePatientPersonalData(getPatientPersonalDataDto());
        verify(patientPersonalDataEntityLookup,times(1)).findIfPatientDataExists(any());
        verify(patientPersonalDataEntityLookup,times(1)).savePatientPersonalData(any());

        assertNotNull(patientDataResponse);
        assertTrue(patientDataResponse.getPatientCreated());
    }

    @Test
    void savePatientPersonalData_DBReturnsNull() {
        when(patientPersonalDataEntityLookup.findIfPatientDataExists(any())).thenReturn(Optional.empty());
        when(patientPersonalDataEntityLookup.savePatientPersonalData(any())).thenReturn(null);
        PatientDataResponse patientDataResponse =
                patientDataResponseProviderService.savePatientPersonalData(getPatientPersonalDataDto());
        verify(patientPersonalDataEntityLookup,times(1)).findIfPatientDataExists(any());
        verify(patientPersonalDataEntityLookup,times(1)).savePatientPersonalData(any());

        assertNotNull(patientDataResponse);
        assertFalse(patientDataResponse.getPatientCreated());
    }

    @Test
    void savePatientPersonalData_CreatedFalse() {
        when(patientPersonalDataEntityLookup.findIfPatientDataExists(any())).thenReturn(Optional.of(getPatientPersonalData()));
        PatientDataResponse patientDataResponse =
                patientDataResponseProviderService.savePatientPersonalData(getPatientPersonalDataDto());
        verify(patientPersonalDataEntityLookup,times(1)).findIfPatientDataExists(any());

        assertNotNull(patientDataResponse);
        assertFalse(patientDataResponse.getPatientCreated());
    }

    @Test
    void retrievePatientPersonalDataByNames_DataFound() {
        when(patientPersonalDataEntityLookup.retrievePatientPersonalDatabyNames(any()))
                .thenReturn(Optional.of(getPatientPersonalDataList()));
        PatientListResponse patientListResponse =
                patientDataResponseProviderService.retrievePatientPersonalDataByNames(new NameSearchQueryParameters("Em","d"));
        verify(patientPersonalDataEntityLookup,times(1)).retrievePatientPersonalDatabyNames(any());
        assertNotNull(patientListResponse);
        assertEquals(patientListResponse.getHttpStatus(), HttpStatus.OK);
    }

    @Test
    void retrievePatientPersonalDataByNames_Null() {
        when(patientPersonalDataEntityLookup.retrievePatientPersonalDatabyNames(any()))
                .thenReturn(Optional.ofNullable(null));
        PatientListResponse patientListResponse =
                patientDataResponseProviderService.retrievePatientPersonalDataByNames(new NameSearchQueryParameters("Em","d"));
        verify(patientPersonalDataEntityLookup,times(1)).retrievePatientPersonalDatabyNames(any());
        assertNotNull(patientListResponse);
        assertEquals(patientListResponse.getHttpStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void retrievePatientPersonalDataByNames_EmptyList() {
        when(patientPersonalDataEntityLookup.retrievePatientPersonalDatabyNames(any()))
                .thenReturn(Optional.of(new ArrayList<>()));
        PatientListResponse patientListResponse =
                patientDataResponseProviderService.retrievePatientPersonalDataByNames(new NameSearchQueryParameters("Em","d"));
        verify(patientPersonalDataEntityLookup,times(1)).retrievePatientPersonalDatabyNames(any());
        assertNotNull(patientListResponse);
        assertEquals(patientListResponse.getHttpStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void retrievePatientPersonalDataByYearsBorn_DataFound() {
        when(patientPersonalDataEntityLookup.retrievePatientPersonalDataByYearsBorn(any()))
                .thenReturn(getYearBornCountDtoList());
        PatientListResponse patientListResponse =
                patientDataResponseProviderService.retrievePatientPersonalDataByYearsBorn(new YearSearchQueryParameters("2020","2022"));
        verify(patientPersonalDataEntityLookup,times(1)).retrievePatientPersonalDataByYearsBorn(any());
        assertNotNull(patientListResponse);
        assertEquals(patientListResponse.getHttpStatus(), HttpStatus.OK);
        assertEquals(patientListResponse.getPatientCntByYrsResult().size(), 2);
    }

    @Test
    void retrievePatientPersonalDataByYearsBorn_Null() {
        when(patientPersonalDataEntityLookup.retrievePatientPersonalDataByYearsBorn(any()))
                .thenReturn(null);
        PatientListResponse patientListResponse =
                patientDataResponseProviderService.retrievePatientPersonalDataByYearsBorn(new YearSearchQueryParameters("2020","2022"));
        verify(patientPersonalDataEntityLookup,times(1)).retrievePatientPersonalDataByYearsBorn(any());
        assertNotNull(patientListResponse);
        assertEquals(patientListResponse.getHttpStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void retrievePatientPersonalDataByYearsBorn_EmptyList() {
        when(patientPersonalDataEntityLookup.retrievePatientPersonalDataByYearsBorn(any()))
                .thenReturn(new ArrayList<>());
        PatientListResponse patientListResponse =
                patientDataResponseProviderService.retrievePatientPersonalDataByYearsBorn(new YearSearchQueryParameters("2020","2022"));
        verify(patientPersonalDataEntityLookup,times(1)).retrievePatientPersonalDataByYearsBorn(any());
        assertNotNull(patientListResponse);
        assertEquals(patientListResponse.getHttpStatus(), HttpStatus.NOT_FOUND);
    }



}