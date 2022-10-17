package com.clinical.portal.patientmgmtapp.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class YearBornCountDto {
    @JsonProperty("yearBorn")
    private Integer year;

    @JsonProperty("numOfPatients")
    private Long total;

    public YearBornCountDto(Integer year, Long total) {
        this.year = year;
        this.total = total;
    }
}
