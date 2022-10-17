package com.clinical.portal.patientmgmtapp.messages.response;

import com.clinical.portal.patientmgmtapp.messages.PatientPersonalDataDto;
import com.clinical.portal.patientmgmtapp.messages.YearBornCountDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientListResponse {
    private String listResponseString;
    @JsonIgnore
    private transient HttpStatus httpStatus;
    private List<PatientPersonalDataDto> patients;
    private List<YearBornCountDto> patientCntByYrsResult;

}
