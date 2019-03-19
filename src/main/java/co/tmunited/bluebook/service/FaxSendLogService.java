package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.FaxSendLog;
import co.tmunited.bluebook.domain.vo.FaxSendLogVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing FaxSendLog.
 */
public interface FaxSendLogService {

    /**
     * Save a faxSendLog.
     *
     * @param faxSendLog the entity to save
     * @return the persisted entity
     */
    FaxSendLog save(FaxSendLog faxSendLog);

    /**
     *  Get all the faxSendLogs.
     *
     *  @return the list of entities
     */
    List<FaxSendLogVO> findAllByFacilityVO(Long facilityId);

    /**
     *  Get the "id" faxSendLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FaxSendLog findOne(Long id);

    /**
     *  Delete the "id" faxSendLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the faxSendLog corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FaxSendLog> search(String query, Pageable pageable);

    Boolean updateFaxState();
    byte[] downloadFaxPdf(Long id);
}
