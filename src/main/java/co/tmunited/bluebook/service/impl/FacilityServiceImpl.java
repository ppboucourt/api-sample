package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.repository.TypeLevelCareRepository;
import co.tmunited.bluebook.repository.TypePatientProcessRepository;
import co.tmunited.bluebook.repository.TypePatientProgramsRepository;
import co.tmunited.bluebook.repository.search.TypeLevelCareSearchRepository;
import co.tmunited.bluebook.repository.search.TypePatientProcessSearchRepository;
import co.tmunited.bluebook.repository.search.TypePatientProgramsSearchRepository;
import co.tmunited.bluebook.service.FacilityService;
import co.tmunited.bluebook.repository.FacilityRepository;
import co.tmunited.bluebook.repository.search.FacilitySearchRepository;
import co.tmunited.bluebook.service.TypePageService;
import co.tmunited.bluebook.service.TypePatientProcessService;
import co.tmunited.bluebook.service.UserService;
import co.tmunited.bluebook.service.util.CsvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Facility.
 */
@Service
@Transactional
public class FacilityServiceImpl implements FacilityService{

    private final Logger log = LoggerFactory.getLogger(FacilityServiceImpl.class);

    @Inject
    private FacilityRepository facilityRepository;

    @Inject
    private FacilitySearchRepository facilitySearchRepository;

    @Inject
    private UserService userService;

    @Inject
    private TypePageService typePageService;

    @Inject
    private TypePatientProcessRepository typePatientProcessRepository;

    @Inject
    private TypePatientProcessSearchRepository typePatientProcessSearchRepository;

    @Inject
    private TypeLevelCareRepository typeLevelCareRepository;

    @Inject
    private TypeLevelCareSearchRepository typeLevelCareSearchRepository;

    @Inject
    private TypePatientProgramsRepository typePatientProgramsRepository;

    @Inject
    private TypePatientProgramsSearchRepository typePatientProgramsSearchRepository;

    /**
     * Save a facility.
     *
     * @param facility the entity to save
     * @return the persisted entity
     */
    public Facility save(Facility facility) {
        log.debug("Request to save Facility : {}", facility);

        Facility result = facilityRepository.save(facility);
        facilitySearchRepository.save(result);

        return result;
    }

    /**
     *  Get all the facilities.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Facility> findAll() {
        log.debug("Request to get all Facilities");
        List<Facility> result = facilityRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one facility by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Facility findOne(Long id) {
        log.debug("Request to get Facility : {}", id);
        Facility facility = facilityRepository.findOne(id);
        return facility;
    }

    /**
     *  Delete the  facility by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Facility : {}", id);
      Facility facility = facilityRepository.findOne(id);
      facility.setDelStatus(true);
      Facility result = facilityRepository.save(facility);

      facilitySearchRepository.save(result);
    }

    /**
     * Search for the facility corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Facility> search(String query) {
        log.debug("Request to search Facilities for query {}", query);
        return StreamSupport
            .stream(facilitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }



    public void initFacility(Facility facility) {

        //Here we init all nomenclators of facility

        User authUser = userService.getUserWithAuthorities();
        TypePage typePage = typePageService.findOne(Long.valueOf(1));//Is 1 because is the id of the typePage FORM

        //Init type patient process
        InputStream inputStream = getClass().getClassLoader()
            .getResourceAsStream("config/liquibase/csv/nomenclator/type_patient_process_init.csv");
        Scanner scanner = new Scanner(inputStream);
        int i = 0;
        while (scanner.hasNext()) {
            List<String> line = CsvUtil.parseLine(scanner.nextLine(), CsvUtil.DEFAULT_SEPARATOR);
            if (i > 0) {
                TypePatientProcess patientProcess = new TypePatientProcess();
                patientProcess.setName(line.get(0));
                patientProcess.setDelStatus(Boolean.FALSE);
                patientProcess.setCreatedBy(authUser.getLogin());
                patientProcess.setLastModifiedBy(authUser.getLogin());
                patientProcess.setCreatedDate(ZonedDateTime.now());
                patientProcess.setLastModifiedDate(ZonedDateTime.now());
                patientProcess.setFacility(facility);
                patientProcess.setTypePage(typePage);
                patientProcess.setStatus("ACT");
                typePatientProcessRepository.save(patientProcess);
                typePatientProcessSearchRepository.save(patientProcess);
            }
            i += 1;
        }
        scanner.close();

        //Init Type Level Care
        inputStream = getClass().getClassLoader()
            .getResourceAsStream("config/liquibase/csv/nomenclator/type_level_care_init.csv");
        scanner = new Scanner(inputStream);
        i = 0;
        while (scanner.hasNext()) {
            List<String> line = CsvUtil.parseLine(scanner.nextLine(), CsvUtil.DEFAULT_SEPARATOR);
            if (i > 0) {
                TypeLevelCare typeLevelCare = new TypeLevelCare();
                typeLevelCare.setName(line.get(0));
                typeLevelCare.setDelStatus(Boolean.FALSE);
                typeLevelCare.setCreatedBy(authUser.getLogin());
                typeLevelCare.setLastModifiedBy(authUser.getLogin());
                typeLevelCare.setCreatedDate(ZonedDateTime.now());
                typeLevelCare.setLastModifiedDate(ZonedDateTime.now());
                typeLevelCare.setStatus("ACT");
                typeLevelCare.setFacility(facility);
                typeLevelCareRepository.save(typeLevelCare);
                typeLevelCareSearchRepository.save(typeLevelCare);
            }
            i += 1;
        }
        scanner.close();

        //Init Type Patient Program
        inputStream = getClass().getClassLoader()
            .getResourceAsStream("config/liquibase/csv/nomenclator/type_patient_programs_init.csv");
        scanner = new Scanner(inputStream);
        i = 0;
        while (scanner.hasNext()) {
            List<String> line = CsvUtil.parseLine(scanner.nextLine(), CsvUtil.DEFAULT_SEPARATOR);
            if (i > 0) {
                TypePatientPrograms typePatientPrograms = new TypePatientPrograms();
                typePatientPrograms.setName(line.get(0));
                typePatientPrograms.setDelStatus(Boolean.FALSE);
                typePatientPrograms.setCreatedBy(authUser.getLogin());
                typePatientPrograms.setLastModifiedBy(authUser.getLogin());
                typePatientPrograms.setCreatedDate(ZonedDateTime.now());
                typePatientPrograms.setLastModifiedDate(ZonedDateTime.now());
                typePatientPrograms.setStatus("ACT");
                typePatientPrograms.setFacility(facility);
                typePatientProgramsRepository.save(typePatientPrograms);
                typePatientProgramsSearchRepository.save(typePatientPrograms);
            }
            i += 1;
        }
        scanner.close();

    }
}
