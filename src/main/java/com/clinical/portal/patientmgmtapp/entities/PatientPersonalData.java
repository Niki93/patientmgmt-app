package com.clinical.portal.patientmgmtapp.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class PatientPersonalData {


    @Id
    @GeneratedValue
    @Column(name = "patient_sid")
    private Long patientSid;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_sid")
    private Address address;

    public void setPatientId(Long patientId) {
        this.patientSid = patientId;
    }

    public Long getPatientId() {
        return patientSid;
    }
}
