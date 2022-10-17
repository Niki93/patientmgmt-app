package com.clinical.portal.patientmgmtapp.messages.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDataResponse {
    private Boolean patientCreated;
    @JsonIgnore
    private transient HttpStatus httpStatus;
    private String listResponseString;
}
