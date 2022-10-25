package com.clinical.portal.patientmgmtapp.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Address {
    @Id
    @Column(name = "patient_sid")
    private Long patientSid;

    private String streetLine1;

    private String streetLine2;

    private int postalCode;

}
