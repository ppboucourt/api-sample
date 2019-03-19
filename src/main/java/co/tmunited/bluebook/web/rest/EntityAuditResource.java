package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.repository.*;
import co.tmunited.bluebook.service.SignatureService;
import co.tmunited.bluebook.web.rest.util.PaginationUtil;
import co.tmunited.bluebook.security.AuthoritiesConstants;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.annotation.Secured;
import com.codahale.metrics.annotation.Timed;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

/**
 * REST controller for getting the audit events for entity
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EntityAuditResource {

    private final Logger log = LoggerFactory.getLogger(EntityAuditResource.class);

    @Inject
    private EntityAuditEventRepository entityAuditEventRepository;

    @Inject
    private Patient_propertiesRepository patient_propertiesRepository;

    @Inject
    private ChartRepository chartRepository;

    @Inject
    private FacilityRepository facilityRepository;

    @Inject
    private ContactsFacilityRepository contactsFacilityRepository;

    @Inject
    private SignatureRepository signatureRepository;

    @Inject
    private SignatureService signatureService;

    /**
     * fetches all the audited entity types
     *
     * @return
     */
    @RequestMapping(value = "/audits/entity/all",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuditedEntities() {
        return entityAuditEventRepository.findAllEntityTypes();
    }

    /**
     * fetches the last 100 change list for an entity class, if limit is passed fetches that many changes
     *
     * @return
     */
    @RequestMapping(value = "/audits/entity/changes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<EntityAuditEvent>> getChanges(@RequestParam(value = "entityType") String entityType,
                                                             @RequestParam(value = "limit") int limit)
        throws URISyntaxException {
        log.debug("REST request to get a page of EntityAuditEvents");
        Pageable pageRequest = createPageRequest(limit);
        Page<EntityAuditEvent> page = entityAuditEventRepository.findAllByEntityTypeAndEntityValueIsNotNull(entityType, pageRequest);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/audits/entity/changes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }

    /**
     * fetches a previous version for for an entity class and id
     *
     * @return
     */
    @RequestMapping(value = "/audits/entity/changes/version/previous",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<EntityAuditEvent> getPrevVersion(@RequestParam(value = "qualifiedName") String qualifiedName,
                                                           @RequestParam(value = "entityId") Long entityId,
                                                           @RequestParam(value = "commitVersion") Integer commitVersion)
        throws URISyntaxException {
        EntityAuditEvent prev = entityAuditEventRepository.findOneByEntityTypeAndEntityIdAndCommitVersion(qualifiedName, entityId, commitVersion);
        return new ResponseEntity<>(prev, HttpStatus.OK);

    }

    /**
     * creates a page request object for PaginationUti
     *
     * @return
     */
    private Pageable createPageRequest(int size) {
        return new PageRequest(0, size);
    }


    /**
     * Update from oldValue to newValue
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/audits/update-to-new-field")
    @Timed
    public ResponseEntity<Boolean> updateToNewField() throws URISyntaxException {

        Boolean result = false;
//        try {
//
//            int pageLimit = 1000;
//            int pageNumber = 0;
//            Page<EntityAuditEvent> page = entityAuditEventRepository.findAll(new PageRequest(pageNumber, pageLimit));
//            while (page.hasNext()) {
//                List<EntityAuditEvent> list = page.getContent();
//                list.forEach(
//                    x -> {
//                        x.setEntityValueTmp(x.getEntityValue());
//                    }
//                );
//                page = entityAuditEventRepository.findAll(new PageRequest(++pageNumber, pageLimit));
//            }
//
//            result = new Boolean(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return new ResponseEntity<>(new Boolean(result), HttpStatus.OK);
    }


    /**
     * Update from oldValue to newValue
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/audits/update-facility-lob-field")
    @Timed
    public ResponseEntity<Boolean> updateFacilityLobField() throws URISyntaxException {

        Boolean result = false;
        try {

            int pageLimit = 1000;
            int pageNumber = 0;
            Page<Facility> page = facilityRepository.findAll(new PageRequest(pageNumber, pageLimit));
            while (page.hasNext()) {
                List<Facility> list = page.getContent();
                list.forEach(
                    x -> {
                        x.getLogo();
                    }
                );
                page = facilityRepository.findAll(new PageRequest(++pageNumber, pageLimit));
            }

            result = new Boolean(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new Boolean(result), HttpStatus.OK);
    }


    /**
     * Update from oldValue to newValue
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/audits/update-signature-lob-field")
    @Timed
    public ResponseEntity<Boolean> updateSignatureLobField() throws URISyntaxException {
        List<Signature> list;
        Boolean result = false;
//        list = signatureRepository.findAll();
//        try {
//            StringBuilder sb = new StringBuilder();
//            list.stream().map(x -> {
//                sb.append("data:image/png;base64,");
//                if(x.getSignature() != null) {
//                    x.setSignatureTmp(StringUtils.newStringUtf8(Base64.encodeBase64(x.getSignature(), false)));
//                }
//                return x;
//            }).collect(Collectors.toList());
//
//            result = new Boolean(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
