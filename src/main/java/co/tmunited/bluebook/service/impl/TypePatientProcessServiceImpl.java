package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypePatientProcessService;
import co.tmunited.bluebook.domain.TypePatientProcess;
import co.tmunited.bluebook.repository.TypePatientProcessRepository;
import co.tmunited.bluebook.repository.search.TypePatientProcessSearchRepository;
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
 * Service Implementation for managing TypePatientProcess.
 */
@Service
@Transactional
public class TypePatientProcessServiceImpl implements TypePatientProcessService{

    private final Logger log = LoggerFactory.getLogger(TypePatientProcessServiceImpl.class);

    @Inject
    private TypePatientProcessRepository typePatientProcessRepository;

    @Inject
    private TypePatientProcessSearchRepository typePatientProcessSearchRepository;

    /**
     * Save a typePatientProcess.
     *
     * @param typePatientProcess the entity to save
     * @return the persisted entity
     */
    public TypePatientProcess save(TypePatientProcess typePatientProcess) {
        log.debug("Request to save TypePatientProcess : {}", typePatientProcess);
        TypePatientProcess result = typePatientProcessRepository.save(typePatientProcess);
        typePatientProcessSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typePatientProcesses.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypePatientProcess> findAll() {
        log.debug("Request to get all TypePatientProcesses");
        List<TypePatientProcess> result = typePatientProcessRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typePatientProcess by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TypePatientProcess findOne(Long id) {
        log.debug("Request to get TypePatientProcess : {}", id);
        TypePatientProcess typePatientProcess = typePatientProcessRepository.findOne(id);
        return typePatientProcess;
    }

    /**
     *  Delete the  typePatientProcess by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypePatientProcess : {}", id);
      TypePatientProcess typePatientProcess = typePatientProcessRepository.findOne(id);
      typePatientProcess.setDelStatus(true);
      TypePatientProcess result = typePatientProcessRepository.save(typePatientProcess);

      typePatientProcessSearchRepository.save(result);
    }

    /**
     * Search for the typePatientProcess corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypePatientProcess> search(String query) {
        log.debug("Request to search TypePatientProcesses for query {}", query);
        return StreamSupport
            .stream(typePatientProcessSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public List<TypePatientProcess> findAllByTypePage(Long pagId, Long facId) {
        List<TypePatientProcess> result = typePatientProcessRepository.findAllByDelStatusIsFalseAndTypePageIdAndFacilityId(pagId, facId);
        return result;
    }

//    @Override
//    public List<TypePatientProcess> findAllVOByTypePage(Long pagId, Long facId) {
//        List<TypePatientProcess> result = typePatientProcessRepository.findAllByDelStatusIsFalseAndTypePageIdAndFacilityId(pagId, facId);
//        return result;
//    }
}
