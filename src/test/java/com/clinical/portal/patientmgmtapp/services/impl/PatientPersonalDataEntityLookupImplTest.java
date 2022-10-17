package com.clinical.portal.patientmgmtapp.services.impl;

import com.clinical.portal.patientmgmtapp.DataBaseClass;
import com.clinical.portal.patientmgmtapp.entities.PatientPersonalData;
import com.clinical.portal.patientmgmtapp.messages.NameSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.YearBornCountDto;
import com.clinical.portal.patientmgmtapp.messages.YearSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.repositories.PatientPersonalDataRepository;
import com.clinical.portal.patientmgmtapp.services.PatientPersonalDataEntityLookup;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class PatientPersonalDataEntityLookupImplTest extends DataBaseClass {

    private PatientPersonalDataEntityLookup patientPersonalDataEntityLookup;

    @Mock
    private PatientPersonalDataRepository patientPersonalDataRepository;

    @BeforeMethod
    public void setUp() throws Exception{
        try{
            MockitoAnnotations.openMocks(this);
            patientPersonalDataEntityLookup = new PatientPersonalDataEntityLookupImpl(patientPersonalDataRepository);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void savePatientPersonalData() {
        when(patientPersonalDataRepository.save(any())).thenReturn(getPatientPersonalData());
        PatientPersonalData patientPersonalData =
                patientPersonalDataEntityLookup.savePatientPersonalData(getPatientPersonalData());
        verify(patientPersonalDataRepository,times(1)).save(any());
        assertNotNull(patientPersonalData);
    }

    @Test
    void findIfPatientDataExists() {
        when(patientPersonalDataRepository
                .findByFirstNameAndAndLastNameAndDateOfBirth(anyString(),anyString(),any()))
                .thenReturn(Optional.of(getPatientPersonalData()));
        Optional<PatientPersonalData> patientPersonalDataOptional =
                patientPersonalDataEntityLookup.findIfPatientDataExists(getPatientPersonalData());
        verify(patientPersonalDataRepository,times(1))
                .findByFirstNameAndAndLastNameAndDateOfBirth(anyString(),anyString(),any());
        assertTrue(patientPersonalDataOptional.isPresent());
        assertNotNull(patientPersonalDataOptional.get());
    }

    @Test
    void retrievePatientPersonalDatabyNamesByFirstAndLastName() {
        when(patientPersonalDataRepository.findByFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase
                (anyString(),anyString())).thenReturn(Optional.of(getPatientPersonalDataList()));
        Optional<List<PatientPersonalData>> optionalPatientPersonalDataList =
                patientPersonalDataEntityLookup.retrievePatientPersonalDatabyNames(new NameSearchQueryParameters("Em","En"));
        verify(patientPersonalDataRepository,times(1))
                .findByFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase(anyString(),anyString());
        assertTrue(optionalPatientPersonalDataList.isPresent());
        assertNotNull(optionalPatientPersonalDataList.get());
        assertTrue(optionalPatientPersonalDataList.get().size()>0);
    }

    @Test
    void retrievePatientPersonalDatabyNamesByFirstName() {
        when(patientPersonalDataRepository.findByFirstNameLikeIgnoreCase(anyString()))
                .thenReturn(Optional.of(getPatientPersonalDataList()));
        Optional<List<PatientPersonalData>> optionalPatientPersonalDataList =
                patientPersonalDataEntityLookup.retrievePatientPersonalDatabyNames(new NameSearchQueryParameters("Em",""));
        verify(patientPersonalDataRepository,times(1))
                .findByFirstNameLikeIgnoreCase(anyString());
        assertTrue(optionalPatientPersonalDataList.isPresent());
        assertNotNull(optionalPatientPersonalDataList.get());
        assertTrue(optionalPatientPersonalDataList.get().size()>0);
    }

    @Test
    void retrievePatientPersonalDatabyNamesByLastName() {
        when(patientPersonalDataRepository.findByLastNameLikeIgnoreCase(anyString()))
                .thenReturn(Optional.of(getPatientPersonalDataList()));
        Optional<List<PatientPersonalData>> optionalPatientPersonalDataList =
                patientPersonalDataEntityLookup.retrievePatientPersonalDatabyNames(new NameSearchQueryParameters("","Em"));
        verify(patientPersonalDataRepository,times(1))
                .findByLastNameLikeIgnoreCase(anyString());
        assertTrue(optionalPatientPersonalDataList.isPresent());
        assertNotNull(optionalPatientPersonalDataList.get());
        assertTrue(optionalPatientPersonalDataList.get().size()>0);
    }

    @Test
    void retrievePatientPersonalData_Null() {
        Optional<List<PatientPersonalData>> optionalPatientPersonalDataList =
                patientPersonalDataEntityLookup.retrievePatientPersonalDatabyNames(new NameSearchQueryParameters("",""));
        verify(patientPersonalDataRepository,times(0))
                .findByLastNameLikeIgnoreCase(anyString());
        assertTrue(optionalPatientPersonalDataList.isEmpty());
    }

    @Test
    void retrievePatientPersonalData_WhiteSpaces() {
        Optional<List<PatientPersonalData>> optionalPatientPersonalDataList =
                patientPersonalDataEntityLookup.retrievePatientPersonalDatabyNames(new NameSearchQueryParameters("  ","  "));
        verify(patientPersonalDataRepository,times(0))
                .findByLastNameLikeIgnoreCase(anyString());
        assertTrue(optionalPatientPersonalDataList.isEmpty());
    }

    @Test
    void retrievePatientPersonalDataByYearsBorn() {
        when(patientPersonalDataRepository.countTotalPatientsByStartAndEndYearsBorn(anyInt(),anyInt()))
                .thenReturn(getYearBornCountDtoList());
        List<YearBornCountDto> yearBornCountDtoList =
                patientPersonalDataEntityLookup.retrievePatientPersonalDataByYearsBorn(new YearSearchQueryParameters("2020","2022"));
        verify(patientPersonalDataRepository,times(1))
                .countTotalPatientsByStartAndEndYearsBorn(anyInt(),anyInt());
        assertNotNull(yearBornCountDtoList);
        assertTrue(yearBornCountDtoList.size()>0);
    }
}