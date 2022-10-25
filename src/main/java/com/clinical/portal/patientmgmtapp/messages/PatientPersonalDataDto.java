package com.clinical.portal.patientmgmtapp.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;


@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PatientPersonalDataDto {

    @NotBlank(message = "cannot be null or empty")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message="should contain only alphabets and spaces")
    @Size(min=2, max=50, message="must be between 2 and 50 characters")
    @JsonProperty("patientFirstName")
    private String firstName;

    @NotBlank(message = "cannot be null or empty")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message="should contain only alphabets and spaces")
    @Size(min=2, max=50, message="must be between 2 and 50 characters")
    @JsonProperty("patientLastName")
    private String lastName;

    @NotNull
    @PastOrPresent
    @JsonProperty("patientDOB")
    private Date dateOfBirth;
}
