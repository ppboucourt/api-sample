package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeMedicationService;
import co.tmunited.bluebook.domain.TypeMedication;
import co.tmunited.bluebook.repository.TypeMedicationRepository;
import co.tmunited.bluebook.repository.search.TypeMedicationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TypeMedication.
 */
@Service
@Transactional
public class TypeMedicationServiceImpl implements TypeMedicationService{

    private final Logger log = LoggerFactory.getLogger(TypeMedicationServiceImpl.class);
    
    @Inject
    private TypeMedicationRepository typeMedicationRepository;

    @Inject
    private TypeMedicationSearchRepository typeMedicationSearchRepository;

    /**
     * Save a typeMedication.
     *
     * @param typeMedication the entity to save
     * @return the persisted entity
     */
    public TypeMedication save(TypeMedication typeMedication) {
        log.debug("Request to save TypeMedication : {}", typeMedication);
        TypeMedication result = typeMedicationRepository.save(typeMedication);
        typeMedicationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeMedications.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeMedication> findAll() {
        log.debug("Request to get all TypeMedications");
        List<TypeMedication> result = typeMedicationRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeMedication by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeMedication findOne(Long id) {
        log.debug("Request to get TypeMedication : {}", id);
        TypeMedication typeMedication = typeMedicationRepository.findOne(id);
        return typeMedication;
    }

    /**
     *  Delete the  typeMedication by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeMedication : {}", id);
      TypeMedication typeMedication = typeMedicationRepository.findOne(id);
      typeMedication.setDelStatus(true);
      TypeMedication result = typeMedicationRepository.save(typeMedication);
      
      typeMedicationSearchRepository.save(result);
    }

    /**
     * Search for the typeMedication corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeMedication> search(String query) {
        log.debug("Request to search TypeMedications for query {}", query);
        return StreamSupport
            .stream(typeMedicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
