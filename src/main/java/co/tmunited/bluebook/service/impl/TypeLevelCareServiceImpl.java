package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.Forms;
import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.service.TypeLevelCareService;
import co.tmunited.bluebook.domain.TypeLevelCare;
import co.tmunited.bluebook.repository.TypeLevelCareRepository;
import co.tmunited.bluebook.repository.search.TypeLevelCareSearchRepository;
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
 * Service Implementation for managing TypeLevelCare.
 */
@Service
@Transactional
public class TypeLevelCareServiceImpl implements TypeLevelCareService{

    private final Logger log = LoggerFactory.getLogger(TypeLevelCareServiceImpl.class);

    @Inject
    private TypeLevelCareRepository typeLevelCareRepository;

    @Inject
    private TypeLevelCareSearchRepository typeLevelCareSearchRepository;

    /**
     * Save a typeLevelCare.
     *
     * @param typeLevelCare the entity to save
     * @return the persisted entity
     */
    public TypeLevelCare save(TypeLevelCare typeLevelCare) {
        log.debug("Request to save TypeLevelCare : {}", typeLevelCare);
        TypeLevelCare result = typeLevelCareRepository.save(typeLevelCare);
        typeLevelCareSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeLevelCares.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeLevelCare> findAll() {
        log.debug("Request to get all TypeLevelCares");
        List<TypeLevelCare> result = typeLevelCareRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeLevelCare by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TypeLevelCare findOne(Long id) {
        log.debug("Request to get TypeLevelCare : {}", id);
        TypeLevelCare typeLevelCare = typeLevelCareRepository.findOne(id);
        return typeLevelCare;
    }

    /**
     *  Delete the  typeLevelCare by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeLevelCare : {}", id);
      TypeLevelCare typeLevelCare = typeLevelCareRepository.findOne(id);
      typeLevelCare.setDelStatus(true);
      TypeLevelCare result = typeLevelCareRepository.save(typeLevelCare);

      typeLevelCareSearchRepository.save(result);
    }

    /**
     * Search for the typeLevelCare corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeLevelCare> search(String query) {
        log.debug("Request to search TypeLevelCares for query {}", query);
        List<TypeLevelCare> result = StreamSupport
            .stream(typeLevelCareSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
        return result;
    }

    public List<Forms> findAllByFacilityAndLevelCareLoadedAutomatic(Long facId, Long lcId) {
        List<Forms> result = typeLevelCareRepository.findAllByFacilityAndLevelCareLoadedAutomatic(facId, lcId);
        return result;
    }

    public List<TypeLevelCare> findAllByFacility(Long id) {
        List<TypeLevelCare> result = typeLevelCareRepository.findAllByFacilityIdAndDelStatusIsFalse(id);
        return result;
    }
}
