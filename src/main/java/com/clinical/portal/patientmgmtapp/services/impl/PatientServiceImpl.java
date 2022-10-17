package com.clinical.portal.patientmgmtapp.services.impl;

import com.clinical.portal.patientmgmtapp.messages.NameSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.PatientPersonalDataDto;
import com.clinical.portal.patientmgmtapp.messages.YearSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.response.PatientDataResponse;
import com.clinical.portal.patientmgmtapp.messages.response.PatientListResponse;
import com.clinical.portal.patientmgmtapp.services.PatientDataResponseProviderService;
import com.clinical.portal.patientmgmtapp.services.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatientServiceImpl implements PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);
    private PatientDataResponseProviderService patientDataResponseProviderService;

    public PatientServiceImpl(PatientDataResponseProviderService patientDataResponseProviderService) {
        this.patientDataResponseProviderService = patientDataResponseProviderService;
    }

    @Override
    public PatientDataResponse savePatientPersonalData(PatientPersonalDataDto patientPersonalDataDto) {
        log.info("Inside PatientServiceImpl, calling patientDataResponseProviderService to save patient data");
        return patientDataResponseProviderService.savePatientPersonalData(patientPersonalDataDto);
    }

    @Override
    public PatientListResponse retrievePatientPersonalDataByNames(NameSearchQueryParameters nameSearchQueryParameters) {
        log.info("Inside PatientServiceImpl, calling patientDataResponseProviderService to retrievePatientPersonalDataByNames");
        return patientDataResponseProviderService.retrievePatientPersonalDataByNames(nameSearchQueryParameters);
    }

    @Override
    public PatientListResponse retrievePatientPersonalDataByYearsBorn(YearSearchQueryParameters yearSearchQueryParameters) {
        log.info("Inside PatientServiceImpl, calling patientDataResponseProviderService to retrievePatientPersonalDataByYearsBorn");
        return patientDataResponseProviderService.retrievePatientPersonalDataByYearsBorn(yearSearchQueryParameters);
    }
}
