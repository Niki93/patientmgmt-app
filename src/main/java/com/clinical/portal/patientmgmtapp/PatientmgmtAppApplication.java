package com.clinical.portal.patientmgmtapp;

import com.clinical.portal.patientmgmtapp.configs.PatientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({PatientConfig.class})
public class PatientmgmtAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientmgmtAppApplication.class, args);
	}

}
