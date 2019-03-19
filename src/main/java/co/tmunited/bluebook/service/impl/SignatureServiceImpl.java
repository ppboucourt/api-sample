package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.SignatureService;
import co.tmunited.bluebook.domain.Signature;
import co.tmunited.bluebook.repository.SignatureRepository;
import co.tmunited.bluebook.repository.search.SignatureSearchRepository;
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
 * Service Implementation for managing Signature.
 */
@Service
@Transactional
public class SignatureServiceImpl implements SignatureService{

    private final Logger log = LoggerFactory.getLogger(SignatureServiceImpl.class);
    
    @Inject
    private SignatureRepository signatureRepository;

    @Inject
    private SignatureSearchRepository signatureSearchRepository;

    /**
     * Save a signature.
     *
     * @param signature the entity to save
     * @return the persisted entity
     */
    public Signature save(Signature signature) {
        log.debug("Request to save Signature : {}", signature);
        Signature result = signatureRepository.save(signature);
        signatureSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the signatures.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Signature> findAll() {
        log.debug("Request to get all Signatures");
        List<Signature> result = signatureRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one signature by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Signature findOne(Long id) {
        log.debug("Request to get Signature : {}", id);
        Signature signature = signatureRepository.findOne(id);
        return signature;
    }

    /**
     *  Delete the  signature by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Signature : {}", id);
      Signature signature = signatureRepository.findOne(id);
      signature.setDelStatus(true);
      Signature result = signatureRepository.save(signature);
      
      signatureSearchRepository.save(result);
    }

    /**
     * Search for the signature corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Signature> search(String query) {
        log.debug("Request to search Signatures for query {}", query);
        return StreamSupport
            .stream(signatureSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
