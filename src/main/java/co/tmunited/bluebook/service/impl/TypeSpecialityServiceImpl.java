package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.TypeSpeciality;
import co.tmunited.bluebook.service.TypeSpecialityService;
import co.tmunited.bluebook.repository.TypeSpecialityRepository;
import co.tmunited.bluebook.repository.search.TypeSpecialitySearchRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.fuzzyQuery;

/**
 * Service Implementation for managing TypeSpeciality.
 */
@Service
@Transactional
public class TypeSpecialityServiceImpl implements TypeSpecialityService{

    private final Logger log = LoggerFactory.getLogger(TypeSpecialityServiceImpl.class);

    @Inject
    private TypeSpecialityRepository TypeSpecialityRepository;

    @Inject
    private TypeSpecialitySearchRepository TypeSpecialitySearchRepository;

    /**
     * Save a TypeSpeciality.
     *
     * @param TypeSpeciality the entity to save
     * @return the persisted entity
     */
    public TypeSpeciality save(TypeSpeciality TypeSpeciality) {
        log.debug("Request to save TypeSpeciality : {}", TypeSpeciality);
        TypeSpeciality result = TypeSpecialityRepository.save(TypeSpeciality);
        TypeSpecialitySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the TypeSpeciality.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeSpeciality> findAll() {
        log.debug("Request to get all TypeSpeciality");
        List<TypeSpeciality> result = TypeSpecialityRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one TypeSpeciality by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TypeSpeciality findOne(Long id) {
        log.debug("Request to get TypeSpeciality : {}", id);
        TypeSpeciality TypeSpeciality = TypeSpecialityRepository.findOne(id);
        return TypeSpeciality;
    }

    /**
     *  Delete the  TypeSpeciality by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeSpeciality : {}", id);
      TypeSpeciality TypeSpeciality = TypeSpecialityRepository.findOne(id);
      TypeSpeciality.setDelStatus(true);
      TypeSpeciality result = TypeSpecialityRepository.save(TypeSpeciality);

      TypeSpecialitySearchRepository.save(result);
    }

    /**
     * Search for the TypeSpeciality corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeSpeciality> search(String query) {
        log.debug("Request to search TypeSpeciality for query {}", query);
        List<TypeSpeciality> result = StreamSupport
            .stream(TypeSpecialitySearchRepository.search(fuzzyQuery("name", query)).spliterator(), false)
            .collect(Collectors.toList());
        return result;
    }
}
