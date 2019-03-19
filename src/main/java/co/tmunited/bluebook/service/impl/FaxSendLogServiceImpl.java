package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.config.Constants;
import co.tmunited.bluebook.config.JHipsterProperties;
import co.tmunited.bluebook.domain.Employee;
import co.tmunited.bluebook.domain.enumeration.FaxState;
import co.tmunited.bluebook.domain.vo.FaxSendLogVO;
import co.tmunited.bluebook.service.FaxSendLogService;
import co.tmunited.bluebook.domain.FaxSendLog;
import co.tmunited.bluebook.repository.FaxSendLogRepository;
import co.tmunited.bluebook.repository.search.FaxSendLogSearchRepository;
import co.tmunited.bluebook.service.UserService;
import co.tmunited.bluebook.service.dto.FileDTO;
import co.tmunited.bluebook.web.rest.client.FileRestClient;
import co.tmunited.tools.rest.RestClient;
import co.tmunited.tools.rest.exception.NotAuthenticatedException;
import com.titicservices.dialer.core.MessageSender;
import com.titicservices.dialer.core.exception.NotInitializedSenderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FaxSendLog.
 */
@Service
@Transactional
public class FaxSendLogServiceImpl implements FaxSendLogService {

    private final Logger log = LoggerFactory.getLogger(FaxSendLogServiceImpl.class);

    @Inject
    private FaxSendLogRepository faxSendLogRepository;

    @Inject
    private FaxSendLogSearchRepository faxSendLogSearchRepository;

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    UserService userService;



    /**
     * Save a faxSendLog.
     *
     * @param faxSendLog the entity to save
     * @return the persisted entity
     */
    public FaxSendLog save(FaxSendLog faxSendLog) {
        log.debug("Request to save FaxSendLog : {}", faxSendLog);
        FaxSendLog result = faxSendLogRepository.save(faxSendLog);
        faxSendLogSearchRepository.save(result);
        return result;
    }



    /**
     * Get all the faxSendLogs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<FaxSendLogVO> findAllByFacilityVO(Long facilityId) {

        log.debug("Request to get all FaxSendLogs");
        List<FaxSendLogVO> result = faxSendLogRepository.findAllByFacilityVO(facilityId);
        return result;
    }


    /**
     * Get one faxSendLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FaxSendLog findOne(Long id) {
        log.debug("Request to get FaxSendLog : {}", id);
        FaxSendLog faxSendLog = faxSendLogRepository.findOne(id);
        return faxSendLog;
    }

    /**
     * Delete the  faxSendLog by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FaxSendLog : {}", id);
        FaxSendLog faxSendLog = findOne(id);
        if (faxSendLog != null) {
            faxSendLog.setDelStatus(Boolean.TRUE);
            faxSendLogSearchRepository.save(faxSendLogRepository.save(faxSendLog));
        }
    }

    /**
     * Search for the faxSendLog corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FaxSendLog> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FaxSendLogs for query {}", query);
        Page<FaxSendLog> result = faxSendLogSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }


    @Transactional()
    public Boolean updateFaxState() {
        log.debug("Request to updateFaxState ");

        List<FaxSendLog> faxSendLogs = faxSendLogRepository.findAllByFinalStatusIsFalse();
        ConcurrentHashMap<Long, FaxSendLog> updateds = new ConcurrentHashMap<>();
        boolean error = false;

        log.info("Update Fax: " + faxSendLogs.size());
        ExecutorService executor = Executors.newWorkStealingPool();

        faxSendLogs.parallelStream().forEach(x -> executor.submit(() -> {
            try {
                updateFaxState(x, updateds);
            } catch (NotInitializedSenderException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }));

        executor.shutdown();

        while (!executor.isTerminated()) {

        }

        updateds.values().stream().forEach(fax -> save(fax));
        return true;
    }


    public byte[] downloadFaxPdf(Long id)  {

        FileDTO result = null;
        FaxSendLog faxSendLog = faxSendLogRepository.findOne(id);
        log.info("Download Pdf from: " + faxSendLog.getId());

        if (faxSendLog != null) {

            try {
                result = FileRestClient.getInstance().getFile(new URL(jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                    jHipsterProperties.getFileStorage().getRemote().getUrlGetFile() + '/' + faxSendLog.getPdfUuid()));

                log.info("PDF uuid: " + result.getUuid());

            } catch (NotAuthenticatedException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return result.getFile().getBytes();

        }

        return null;
    }


    /**
     * @RequestMapping(path = "/file/dto/{id}", method = RequestMethod.GET)
     * public FileDTO getFileById(@PathVariable Long id) throws IOException, RuntimeException {
     * FileDTO result = null;
     * try {
     * <p>
     * File file = fileService.findOne(id);
     * <p>
     * result = FileRestClient.getInstance().getFile(new URL(jHipsterProperties.getFileStorage().getRemote().getBasePath() +
     * jHipsterProperties.getFileStorage().getRemote().getUrlGetFile() + '/' + file.getUuid()));
     * } catch (RuntimeException e) {
     * <p>
     * } catch (NotAuthenticatedException e) {
     * e.printStackTrace();
     * }
     * return result;
     * }
     */


    private void updateFaxState(FaxSendLog faxSendLog, ConcurrentHashMap<Long, FaxSendLog> updateds) throws NotInitializedSenderException, URISyntaxException {

        MessageSender sender = MessageSender.getInstance();
        String newState = sender.retrieveFax(faxSendLog.getSid()).getStatus().toString();
        log.info("New status for " + faxSendLog.getSid() + ": " + newState);


        faxSendLog.setFaxState(findState(newState));

        faxSendLog.setFinalStatus(isFinalState(faxSendLog));

        updateds.put(faxSendLog.getId(), faxSendLog);
    }


    private FaxState findState(String val) {
        FaxState newState = Arrays.stream(FaxState.values())
            .filter(e -> e.name().replace("_", "-").toLowerCase().equals(val))
            .findFirst().get();

        log.info("New status: " + newState.name());

        return newState;

    }

    private boolean isFinalState(FaxSendLog faxSendLog) {

        if (Constants.FAX_FINAL_STATUS.contains(faxSendLog.getFaxState().name())) {
            return true;
        }

        return false;
    }


    public Logger getLog() {
        return log;
    }

    public FaxSendLogRepository getFaxSendLogRepository() {
        return faxSendLogRepository;
    }

    public void setFaxSendLogRepository(FaxSendLogRepository faxSendLogRepository) {
        this.faxSendLogRepository = faxSendLogRepository;
    }

    public FaxSendLogSearchRepository getFaxSendLogSearchRepository() {
        return faxSendLogSearchRepository;
    }

    public void setFaxSendLogSearchRepository(FaxSendLogSearchRepository faxSendLogSearchRepository) {
        this.faxSendLogSearchRepository = faxSendLogSearchRepository;
    }

    public JHipsterProperties getjHipsterProperties() {
        return jHipsterProperties;
    }

    public void setjHipsterProperties(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

}
