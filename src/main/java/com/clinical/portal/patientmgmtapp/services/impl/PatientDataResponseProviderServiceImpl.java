package com.clinical.portal.patientmgmtapp.services.impl;

import com.clinical.portal.patientmgmtapp.entities.PatientPersonalData;
import com.clinical.portal.patientmgmtapp.messages.NameSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.YearBornCountDto;
import com.clinical.portal.patientmgmtapp.messages.YearSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.response.PatientDataResponse;
import com.clinical.portal.patientmgmtapp.messages.response.PatientListResponse;
import com.clinical.portal.patientmgmtapp.messages.PatientPersonalDataDto;
import com.clinical.portal.patientmgmtapp.services.PatientDataResponseProviderService;
import com.clinical.portal.patientmgmtapp.services.PatientPersonalDataEntityLookup;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PatientDataResponseProviderServiceImpl implements PatientDataResponseProviderService {
    private static final Logger log = LoggerFactory.getLogger(PatientDataResponseProviderServiceImpl.class);
    private PatientPersonalDataEntityLookup patientPersonalDataEntityLookup;

    private ModelMapper modelMapper;

    private final String listNotFoundResponse = "No data found.";

    private final String dataConflictResponse = "Conflict Encountered during saving data";
    public PatientDataResponseProviderServiceImpl(PatientPersonalDataEntityLookup patientPersonalDataEntityLookup,
                                                  ModelMapper modelMapper) {
        this.patientPersonalDataEntityLookup = patientPersonalDataEntityLookup;
        this.modelMapper = modelMapper;
    }

    @Override
    public PatientDataResponse savePatientPersonalData(PatientPersonalDataDto patientPersonalDataDto) {
        log.info("Inside PatientDataResponseProviderServiceImpl savePatientPersonalData");
        //Search if patient exists first
        //Map Dto to Entity Object
        PatientPersonalData patientPersonalData = convertToEntity(patientPersonalDataDto);
        PatientDataResponse patientDataResponse = new PatientDataResponse();

        Optional<PatientPersonalData> patientDataOpt =
                patientPersonalDataEntityLookup.findIfPatientDataExists(patientPersonalData);
        if(patientDataOpt.isPresent()){
            //Patient Personal Data is Present in DB, hence no need to save as record exists
            patientDataResponse.setHttpStatus(HttpStatus.OK);
            patientDataResponse.setPatientCreated(false);
            return patientDataResponse;
        }

        //Patient Personal Data not found
        PatientPersonalData data = patientPersonalDataEntityLookup.savePatientPersonalData(patientPersonalData);
        if(data !=null){
            patientDataResponse.setHttpStatus(HttpStatus.CREATED);
            patientDataResponse.setPatientCreated(true);
        }else{
            patientDataResponse.setHttpStatus(HttpStatus.CONFLICT);
            patientDataResponse.setPatientCreated(false);
            patientDataResponse.setListResponseString(dataConflictResponse);
        }

        return patientDataResponse;
    }

    @Override
    public PatientListResponse retrievePatientPersonalDataByNames(NameSearchQueryParameters nameSearchQueryParameters) {
        log.info("Inside PatientDataResponseProviderServiceImpl retrievePatientPersonalDataByNames");
        Optional<List<PatientPersonalData>> patientPersonalDataList =
                patientPersonalDataEntityLookup.retrievePatientPersonalDatabyNames(nameSearchQueryParameters);
        PatientListResponse patientListResponse = new PatientListResponse();

        //check if List is Not Empty and has a record
        if(patientPersonalDataList.isPresent() && !patientPersonalDataList.get().isEmpty()){
            List<PatientPersonalDataDto> patientPersonalDataDtoList =
                    patientPersonalDataList.get().stream()
                    .map(patientPersonalData -> convertToDto(patientPersonalData))
                    .collect(Collectors.toList());

            patientListResponse.setHttpStatus(HttpStatus.OK);
            patientListResponse.setPatients(patientPersonalDataDtoList);

        }else{
            patientListResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            patientListResponse.setListResponseString(listNotFoundResponse);
        }

        return patientListResponse;
    }

    @Override
    public PatientListResponse retrievePatientPersonalDataByYearsBorn(YearSearchQueryParameters yearSearchQueryParameters) {
        log.info("Inside PatientDataResponseProviderServiceImpl retrievePatientPersonalDataByYearsBorn");
        List<YearBornCountDto> yearBornCountDtoList = patientPersonalDataEntityLookup
                .retrievePatientPersonalDataByYearsBorn(yearSearchQueryParameters);
        PatientListResponse patientListResponse = new PatientListResponse();
        //Setting the appropiate responses
        if(yearBornCountDtoList!=null && yearBornCountDtoList.size()>0){
            patientListResponse.setHttpStatus(HttpStatus.OK);
            patientListResponse.setPatientCntByYrsResult(yearBornCountDtoList);
        }else{
            patientListResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            patientListResponse.setListResponseString(listNotFoundResponse);
        }
        return patientListResponse;
    }

    private PatientPersonalDataDto convertToDto(PatientPersonalData patientPersonalData){
        log.info("Converting Entity to PatientPersonalDataDto");
        return modelMapper.map(patientPersonalData,PatientPersonalDataDto.class);
    }

    public PatientPersonalData convertToEntity(PatientPersonalDataDto patientPersonalDataDto){
        log.info("Converting PatientPersonalDataDto to Entity");
        return modelMapper.map(patientPersonalDataDto, PatientPersonalData.class);
    }
}
