package com.clinical.portal.patientmgmtapp.messages;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Data
public class YearSearchQueryParameters {
    @NotNull
    @Pattern(regexp = "^[0-9]{4}$", message = "Only upto 4 numbers allowed.")
    private String startYear;
    @NotNull
    @Pattern(regexp = "^[0-9]{4}$", message = "Only upto 4 numbers allowed.")
    private String endYear;

    public YearSearchQueryParameters(String startYear, String endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }
}
