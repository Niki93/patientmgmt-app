package com.clinical.portal.patientmgmtapp.services;

import com.clinical.portal.patientmgmtapp.messages.NameSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.YearSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.response.PatientDataResponse;
import com.clinical.portal.patientmgmtapp.messages.response.PatientListResponse;
import com.clinical.portal.patientmgmtapp.messages.PatientPersonalDataDto;

public interface PatientDataResponseProviderService {

    PatientDataResponse savePatientPersonalData(PatientPersonalDataDto patientPersonalDataDto);

    PatientListResponse retrievePatientPersonalDataByNames(NameSearchQueryParameters nameSearchQueryParameters);

    PatientListResponse retrievePatientPersonalDataByYearsBorn(YearSearchQueryParameters yearSearchQueryParameters);

}
