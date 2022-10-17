package com.clinical.portal.patientmgmtapp.services.impl;

import com.clinical.portal.patientmgmtapp.DataBaseClass;
import com.clinical.portal.patientmgmtapp.messages.NameSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.YearSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.services.PatientDataResponseProviderService;
import com.clinical.portal.patientmgmtapp.services.PatientService;
import org.testng.annotations.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PatientServiceImplTest extends DataBaseClass {

    private PatientService patientService;

    @Mock
    private PatientDataResponseProviderService patientDataResponseProviderService;

    @BeforeMethod
    public void setUp() throws Exception{
        try{
            MockitoAnnotations.openMocks(this);
            patientService = new PatientServiceImpl(patientDataResponseProviderService);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void savePatientPersonalData() {
        when(patientDataResponseProviderService.savePatientPersonalData(any())).thenReturn(getPatientDataResponse());
        patientService.savePatientPersonalData(getPatientPersonalDataDto());
        verify(patientDataResponseProviderService,times(1)).savePatientPersonalData(any());
    }

    @Test
    void retrievePatientPersonalDataByNames() {
        when(patientDataResponseProviderService.retrievePatientPersonalDataByNames(any())).thenReturn(getPatientListResponseByNames());
        patientService.retrievePatientPersonalDataByNames(new NameSearchQueryParameters("Em","En"));
        verify(patientDataResponseProviderService,times(1)).retrievePatientPersonalDataByNames(any());
    }

    @Test
    void retrievePatientPersonalDataByYearsBorn() {
        when(patientDataResponseProviderService.retrievePatientPersonalDataByYearsBorn(any())).thenReturn(getPatientListResponseByYears());
        patientService.retrievePatientPersonalDataByYearsBorn(new YearSearchQueryParameters("2020","2022"));
        verify(patientDataResponseProviderService,times(1)).retrievePatientPersonalDataByYearsBorn(any());
    }
}