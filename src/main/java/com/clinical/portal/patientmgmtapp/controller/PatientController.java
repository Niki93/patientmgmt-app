package com.clinical.portal.patientmgmtapp.controller;

import com.clinical.portal.patientmgmtapp.messages.NameSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.PatientPersonalDataDto;
import com.clinical.portal.patientmgmtapp.messages.YearSearchQueryParameters;
import com.clinical.portal.patientmgmtapp.messages.response.PatientDataResponse;
import com.clinical.portal.patientmgmtapp.messages.response.PatientListResponse;
import com.clinical.portal.patientmgmtapp.services.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/clinical/portal")
@Validated
public class PatientController {

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(path="/patients", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity savePatientData(@RequestBody @Valid PatientPersonalDataDto patientPersonalDataDto){
        log.info("PatientController to savePatientData.");
        PatientDataResponse patientDataResponse = patientService.savePatientPersonalData(patientPersonalDataDto);
        return ResponseEntity.status(patientDataResponse.getHttpStatus()).body(patientDataResponse);
    }

    @GetMapping(path="/patients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity retrievePatientData(@Valid NameSearchQueryParameters nameSearchQueryParameters){
        log.info("PatientController to retrievePatientData by names.");
        PatientListResponse patientListResponse =
                patientService.retrievePatientPersonalDataByNames(nameSearchQueryParameters);
        return ResponseEntity.status(patientListResponse.getHttpStatus()).body(patientListResponse);
    }

    @GetMapping(path="/patients/yearcount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity retrievePatientCountByYears(@Valid YearSearchQueryParameters yearSearchQueryParameters){
        log.info("PatientController to retrievePatientData by the year they are born in.");
        PatientListResponse patientListResponse =
                patientService.retrievePatientPersonalDataByYearsBorn(yearSearchQueryParameters);
        return ResponseEntity.status(patientListResponse.getHttpStatus()).body(patientListResponse);
    }
}
