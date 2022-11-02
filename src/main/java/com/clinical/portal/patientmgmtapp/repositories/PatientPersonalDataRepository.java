package com.clinical.portal.patientmgmtapp.repositories;

import com.clinical.portal.patientmgmtapp.entities.PatientPersonalData;
import com.clinical.portal.patientmgmtapp.messages.YearBornCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface PatientPersonalDataRepository
        extends JpaRepository<PatientPersonalData, Long> {

    Optional<PatientPersonalData> findByFirstNameAndAndLastNameAndDateOfBirth(String firstName, String lasName, Date dataOfBirth);

    Optional<List<PatientPersonalData>> findByFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase(String firstName, String lasName);

    Optional<List<PatientPersonalData>> findByFirstNameLikeIgnoreCase(String firstName);

    Optional<List<PatientPersonalData>> findByLastNameLikeIgnoreCase(String lastName);

    @Query("SELECT new com.clinical.portal.patientmgmtapp.messages.YearBornCountDto(YEAR(ppd.dateOfBirth), COUNT(ppd.dateOfBirth))  "
            + "FROM PatientPersonalData AS ppd where YEAR(ppd.dateOfBirth) >= ?1 AND YEAR(ppd.dateOfBirth) < ?2 "
            + "GROUP BY YEAR(ppd.dateOfBirth) ORDER BY COUNT(ppd.dateOfBirth) DESC")
    List<YearBornCountDto> countTotalPatientsByStartAndEndYearsBorn(int startYear, int endYear);

}
