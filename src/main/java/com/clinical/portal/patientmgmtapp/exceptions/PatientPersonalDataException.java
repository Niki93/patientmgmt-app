package com.clinical.portal.patientmgmtapp.exceptions;

import com.clinical.portal.patientmgmtapp.messages.response.Detail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class PatientPersonalDataException {
    private HttpStatus httpStatus;

    private List<Detail> details;
}
