package com.clinical.portal.patientmgmtapp.configs;

import com.clinical.portal.patientmgmtapp.repositories.PatientPersonalDataRepository;
import com.clinical.portal.patientmgmtapp.services.PatientDataResponseProviderService;
import com.clinical.portal.patientmgmtapp.services.PatientPersonalDataEntityLookup;
import com.clinical.portal.patientmgmtapp.services.PatientService;
import com.clinical.portal.patientmgmtapp.services.impl.PatientDataResponseProviderServiceImpl;
import com.clinical.portal.patientmgmtapp.services.impl.PatientPersonalDataEntityLookupImpl;
import com.clinical.portal.patientmgmtapp.services.impl.PatientServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.clinical.portal.patientmgmtapp"})
public class PatientConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PatientService patientService(PatientDataResponseProviderService patientDataResponseProviderService){
        return new PatientServiceImpl(patientDataResponseProviderService);
    }

    @Bean
    public PatientDataResponseProviderService patientDataResponseProviderService(
            PatientPersonalDataEntityLookup patientPersonalDataEntityLookup,
            ModelMapper modelMapper){
        return new PatientDataResponseProviderServiceImpl(patientPersonalDataEntityLookup,modelMapper);
    }

    @Bean
    public PatientPersonalDataEntityLookup patientPersonalDataEntityLookup(PatientPersonalDataRepository patientPersonalDataRepository){
        return new PatientPersonalDataEntityLookupImpl(patientPersonalDataRepository);
    }
}
