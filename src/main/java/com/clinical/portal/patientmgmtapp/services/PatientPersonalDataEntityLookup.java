package com.clinical.portal.patientmgmtapp.services;

import com.clinical.portal.patientmgmtapp.entities.PatientPersonalData;
import com.clinical.portal.patientmgmtapp.messages.NameSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.YearBornCountDto;
import com.clinical.portal.patientmgmtapp.messages.YearSearchQueryParameters;

import java.util.List;
import java.util.Optional;

public interface PatientPersonalDataEntityLookup {

    PatientPersonalData savePatientPersonalData(PatientPersonalData patientPersonalData);

    Optional<PatientPersonalData> findIfPatientDataExists(PatientPersonalData patientPersonalData);

    Optional<List<PatientPersonalData>> retrievePatientPersonalDatabyNames(NameSearchQueryParameters nameSearchQueryParameters);

    List<YearBornCountDto> retrievePatientPersonalDataByYearsBorn(YearSearchQueryParameters yearSearchQueryParameters);
}
