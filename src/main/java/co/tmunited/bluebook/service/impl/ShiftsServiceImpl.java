package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.File;
import co.tmunited.bluebook.domain.vo.ShiftVO;
import co.tmunited.bluebook.service.FileService;
import co.tmunited.bluebook.service.ShiftsService;
import co.tmunited.bluebook.domain.Shifts;
import co.tmunited.bluebook.repository.ShiftsRepository;
import co.tmunited.bluebook.repository.search.ShiftsSearchRepository;
import co.tmunited.bluebook.service.dto.FileDTO;
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
 * Service Implementation for managing Shifts.
 */
@Service
@Transactional
public class ShiftsServiceImpl implements ShiftsService {

    private final Logger log = LoggerFactory.getLogger(ShiftsServiceImpl.class);

    @Inject
    private ShiftsRepository shiftsRepository;

    @Inject
    private ShiftsSearchRepository shiftsSearchRepository;

    @Inject
    private FileService fileService;

    /**
     * Save a shifts.
     *
     * @param shifts the entity to save
     * @return the persisted entity
     */
    public Shifts save(Shifts shifts) {
        log.debug("Request to save Shifts : {}", shifts);
        Shifts result = shiftsRepository.save(shifts);
        shiftsSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the shifts.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Shifts> findAll() {
        log.debug("Request to get all Shifts");
        List<Shifts> result = shiftsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get all the shifts.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ShiftVO> findAllShifts() {
        log.debug("Request to get all Shifts");
        return shiftsRepository.findAllByDelStatusIsFalseOrderByCreatedDateVO();
    }

    /**
     * Get one shifts by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Shifts findOne(Long id) {
        log.debug("Request to get Shifts : {}", id);
        Shifts shifts = shiftsRepository.findOne(id);
        return shifts;
    }

    /**
     * Delete the  shifts by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Shifts : {}", id);
        Shifts shifts = shiftsRepository.findOne(id);
        shifts.setDelStatus(true);
        Shifts result = shiftsRepository.save(shifts);

        shiftsSearchRepository.save(result);
    }

    /**
     * Search for the shifts corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Shifts> search(String query) {
        log.debug("Request to search Shifts for query {}", query);
        return StreamSupport
            .stream(shiftsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @Override
    public File attachFile(FileDTO fileDTO) {
        return fileService.attachFile(fileDTO);
    }

    @Override
    public void deleteFile(Long id) {
        fileService.delete(id);
    }
}
