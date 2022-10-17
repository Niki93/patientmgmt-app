package com.clinical.portal.patientmgmtapp.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Data
public class PatientPersonalData {


    @Id
    @GeneratedValue
    private Long patientSid;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    public void setPatientId(Long patientId) {
        this.patientSid = patientId;
    }

    public Long getPatientId() {
        return patientSid;
    }
}
