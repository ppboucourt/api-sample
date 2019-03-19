package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeMedicationRoutesService;
import co.tmunited.bluebook.domain.TypeMedicationRoutes;
import co.tmunited.bluebook.repository.TypeMedicationRoutesRepository;
import co.tmunited.bluebook.repository.search.TypeMedicationRoutesSearchRepository;
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
 * Service Implementation for managing TypeMedicationRoutes.
 */
@Service
@Transactional
public class TypeMedicationRoutesServiceImpl implements TypeMedicationRoutesService{

    private final Logger log = LoggerFactory.getLogger(TypeMedicationRoutesServiceImpl.class);
    
    @Inject
    private TypeMedicationRoutesRepository typeMedicationRoutesRepository;

    @Inject
    private TypeMedicationRoutesSearchRepository typeMedicationRoutesSearchRepository;

    /**
     * Save a typeMedicationRoutes.
     *
     * @param typeMedicationRoutes the entity to save
     * @return the persisted entity
     */
    public TypeMedicationRoutes save(TypeMedicationRoutes typeMedicationRoutes) {
        log.debug("Request to save TypeMedicationRoutes : {}", typeMedicationRoutes);
        TypeMedicationRoutes result = typeMedicationRoutesRepository.save(typeMedicationRoutes);
        typeMedicationRoutesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeMedicationRoutes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeMedicationRoutes> findAll() {
        log.debug("Request to get all TypeMedicationRoutes");
        List<TypeMedicationRoutes> result = typeMedicationRoutesRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeMedicationRoutes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeMedicationRoutes findOne(Long id) {
        log.debug("Request to get TypeMedicationRoutes : {}", id);
        TypeMedicationRoutes typeMedicationRoutes = typeMedicationRoutesRepository.findOne(id);
        return typeMedicationRoutes;
    }

    /**
     *  Delete the  typeMedicationRoutes by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeMedicationRoutes : {}", id);
      TypeMedicationRoutes typeMedicationRoutes = typeMedicationRoutesRepository.findOne(id);
      typeMedicationRoutes.setDelStatus(true);
      TypeMedicationRoutes result = typeMedicationRoutesRepository.save(typeMedicationRoutes);
      
      typeMedicationRoutesSearchRepository.save(result);
    }

    /**
     * Search for the typeMedicationRoutes corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeMedicationRoutes> search(String query) {
        log.debug("Request to search TypeMedicationRoutes for query {}", query);
        return StreamSupport
            .stream(typeMedicationRoutesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
