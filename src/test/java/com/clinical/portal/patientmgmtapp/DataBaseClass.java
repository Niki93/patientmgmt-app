package com.clinical.portal.patientmgmtapp;

import com.clinical.portal.patientmgmtapp.entities.PatientPersonalData;
import com.clinical.portal.patientmgmtapp.messages.PatientPersonalDataDto;
import com.clinical.portal.patientmgmtapp.messages.YearBornCountDto;
import com.clinical.portal.patientmgmtapp.messages.response.PatientDataResponse;
import com.clinical.portal.patientmgmtapp.messages.response.PatientListResponse;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DataBaseClass {

    public PatientPersonalDataDto getPatientPersonalDataDto(){
        PatientPersonalDataDto patientPersonalDataDto = new PatientPersonalDataDto();
        patientPersonalDataDto.setFirstName("Emma");
        patientPersonalDataDto.setLastName("Richards");
        patientPersonalDataDto.setDateOfBirth(Date.valueOf("2021-02-03"));
        return patientPersonalDataDto;
    }

    public PatientPersonalData getPatientPersonalData(){
        PatientPersonalData patientPersonalData = new PatientPersonalData();
        patientPersonalData.setFirstName("Emma");
        patientPersonalData.setLastName("Richards");
        patientPersonalData.setDateOfBirth(Date.valueOf("2021-02-03"));
        return patientPersonalData;
    }

    public PatientPersonalData getPatientPersonalData(String firstName, String lastName, Date dob){
        PatientPersonalData patientPersonalData = new PatientPersonalData();
        patientPersonalData.setFirstName(firstName);
        patientPersonalData.setLastName(lastName);
        patientPersonalData.setDateOfBirth(dob);
        return patientPersonalData;
    }

    public List<PatientPersonalData> getPatientPersonalDataList(){
        List<PatientPersonalData> patientPersonalDataList = new ArrayList<>();
        patientPersonalDataList.add(getPatientPersonalData("Emma","Richard",Date.valueOf("2021-02-03")));
        patientPersonalDataList.add(getPatientPersonalData("Jemma","Richard",Date.valueOf("2020-02-03")));
        patientPersonalDataList.add(getPatientPersonalData("Semma","Richard",Date.valueOf("2019-02-03")));
        return patientPersonalDataList;
    }

    public PatientListResponse getPatientListResponseByNames(){
        PatientListResponse patientListResponse = new PatientListResponse();
        List<PatientPersonalDataDto> patients = new ArrayList<>();
        patients.add(getPatientPersonalDataDto());
        patientListResponse.setHttpStatus(HttpStatus.OK);
        patientListResponse.setPatients(patients);
        return patientListResponse;
    }

    public PatientDataResponse getPatientDataResponse(){
        PatientDataResponse patientDataResponse = new PatientDataResponse();
        patientDataResponse.setPatientCreated(Boolean.TRUE);
        patientDataResponse.setHttpStatus(HttpStatus.CREATED);

        return patientDataResponse;
    }

    public PatientListResponse getPatientListResponseByYears(){
        PatientListResponse patientListResponse = new PatientListResponse();
        List<YearBornCountDto> yearBornCountDtoList = getYearBornCountDtoList();
        patientListResponse.setHttpStatus(HttpStatus.OK);
        patientListResponse.setPatientCntByYrsResult(yearBornCountDtoList);
        return patientListResponse;
    }

    public List<YearBornCountDto> getYearBornCountDtoList(){
        List<YearBornCountDto> yearBornCountDtoList = new ArrayList<>();
        yearBornCountDtoList.add(new YearBornCountDto(2020,123l));
        yearBornCountDtoList.add(new YearBornCountDto(2021,120l));
        return yearBornCountDtoList;
    }
}
