package com.clinical.portal.patientmgmtapp.services.impl;

import com.clinical.portal.patientmgmtapp.entities.Address;
import com.clinical.portal.patientmgmtapp.entities.PatientPersonalData;
import com.clinical.portal.patientmgmtapp.messages.NameSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.YearBornCountDto;
import com.clinical.portal.patientmgmtapp.messages.YearSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.repositories.PatientPersonalDataRepository;
import com.clinical.portal.patientmgmtapp.services.PatientPersonalDataEntityLookup;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public class PatientPersonalDataEntityLookupImpl implements PatientPersonalDataEntityLookup {
    private static final Logger log = LoggerFactory.getLogger(PatientPersonalDataEntityLookupImpl.class);

    private PatientPersonalDataRepository patientPersonalDataRepository;

    public PatientPersonalDataEntityLookupImpl(PatientPersonalDataRepository patientPersonalDataRepository) {
        this.patientPersonalDataRepository = patientPersonalDataRepository;
    }

    @Override
    @Transactional
    public PatientPersonalData savePatientPersonalData(PatientPersonalData patientPersonalData) {
        log.info("Save Patient Data to DB");
        PatientPersonalData patientPersonalDataSaved = patientPersonalDataRepository.save(patientPersonalData);
        Address address = new Address();
        address.setPatientSid(patientPersonalDataSaved.getPatientSid());

        address.setStreetLine1("Hobsonville");
        address.setStreetLine2("Parking Road");
        address.setPostalCode(87923);

        patientPersonalData.setAddress(address);

        patientPersonalDataRepository.save(patientPersonalData);

        //patientPersonalDataRepository.findById();

        return patientPersonalDataSaved;
    }

    @Override
    public Optional<PatientPersonalData> findPatientDataById(Long patientId){
        return patientPersonalDataRepository.findById(patientId);
    }

    public Optional<PatientPersonalData> findIfPatientDataExists(PatientPersonalData patientPersonalData){
        return patientPersonalDataRepository.findByFirstNameAndAndLastNameAndDateOfBirth(patientPersonalData.getFirstName(),
                patientPersonalData.getLastName(),patientPersonalData.getDateOfBirth());
    }

    public Optional<List<PatientPersonalData>> retrievePatientPersonalDatabyNames(NameSearchQueryParameters nameSearchQueryParameters){

        log.info("Constructing the like query parameters.");
        Pair<String,String> searchStrPairs =
                getNameSearchStrings(nameSearchQueryParameters);

        if(searchStrPairs.getLeft()!=null && searchStrPairs.getRight()!=null)
            return patientPersonalDataRepository.findByFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase
                    (searchStrPairs.getLeft(),searchStrPairs.getRight());

        if(searchStrPairs.getLeft()!=null)
            return patientPersonalDataRepository.findByFirstNameLikeIgnoreCase(searchStrPairs.getLeft());

        if(searchStrPairs.getRight()!=null)
            return patientPersonalDataRepository.findByLastNameLikeIgnoreCase(searchStrPairs.getRight());

        return Optional.ofNullable(null);

    }

    public List<YearBornCountDto> retrievePatientPersonalDataByYearsBorn(YearSearchQueryParameters yearSearchQueryParameters) {
        log.info("retrievePatientPersonalDataByYearsBorn to start.");
        int endYear = Integer.parseInt(yearSearchQueryParameters.getEndYear()) +1;
        return patientPersonalDataRepository.countTotalPatientsByStartAndEndYearsBorn
                (Integer.parseInt(yearSearchQueryParameters.getStartYear()), endYear);
    }

    private Pair<String, String> getNameSearchStrings(NameSearchQueryParameters nameSearchQueryParameters){
        String firstName = nameSearchQueryParameters.getFirstName();
        String lastName = nameSearchQueryParameters.getLastName();

        String firstNameSearchStr;
        String lastNameSearchStr;

        if(firstName!=null && StringUtils.isNotEmpty(StringUtils.strip(firstName,null))){
            firstNameSearchStr = "%"+StringUtils.strip(firstName,null)+"%";
        }else
            firstNameSearchStr = null;

        if(lastName!=null && StringUtils.isNotEmpty(StringUtils.strip(lastName,null))){
            lastNameSearchStr = "%"+StringUtils.strip(lastName,null)+"%";
        }else
            lastNameSearchStr = null;

        return new ImmutablePair<>(firstNameSearchStr,lastNameSearchStr);
    }
}
