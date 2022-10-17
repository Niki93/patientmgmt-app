package com.clinical.portal.patientmgmtapp.messages.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class Detail {

    private String location;
    private String message;
}
