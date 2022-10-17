package com.clinical.portal.patientmgmtapp.messages;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
public class NameSearchQueryParameters {
    @Pattern(regexp = "^[A-Za-z]+$", message="should contain only alphabets or spaces")
    @Size(max=50, message="must be 50 characters or less")
    private String firstName;
    @Pattern(regexp = "^[A-Za-z]+$", message="should contain only alphabets or spaces")
    @Size(max=50, message="must be 50 characters or less")
    private String lastName;

    public NameSearchQueryParameters(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
