package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.GroupType;
import co.tmunited.bluebook.service.GroupTypeService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing GroupType.
 */
@RestController
@RequestMapping("/api")
public class GroupTypeResource {

    private final Logger log = LoggerFactory.getLogger(GroupTypeResource.class);
        
    @Inject
    private GroupTypeService groupTypeService;

    /**
     * POST  /group-types : Create a new groupType.
     *
     * @param groupType the groupType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupType, or with status 400 (Bad Request) if the groupType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-types")
    @Timed
    public ResponseEntity<GroupType> createGroupType(@RequestBody GroupType groupType) throws URISyntaxException {
        log.debug("REST request to save GroupType : {}", groupType);
        if (groupType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("groupType", "idexists", "A new groupType cannot already have an ID")).body(null);
        }
        GroupType result = groupTypeService.save(groupType);
        return ResponseEntity.created(new URI("/api/group-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("groupType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-types : Updates an existing groupType.
     *
     * @param groupType the groupType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupType,
     * or with status 400 (Bad Request) if the groupType is not valid,
     * or with status 500 (Internal Server Error) if the groupType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-types")
    @Timed
    public ResponseEntity<GroupType> updateGroupType(@RequestBody GroupType groupType) throws URISyntaxException {
        log.debug("REST request to update GroupType : {}", groupType);
        if (groupType.getId() == null) {
            return createGroupType(groupType);
        }
        GroupType result = groupTypeService.save(groupType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("groupType", groupType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-types : get all the groupTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groupTypes in body
     */
    @GetMapping("/group-types")
    @Timed
    public List<GroupType> getAllGroupTypes() {
        log.debug("REST request to get all GroupTypes");
        return groupTypeService.findAll();
    }

    /**
     * GET  /group-types/:id : get the "id" groupType.
     *
     * @param id the id of the groupType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupType, or with status 404 (Not Found)
     */
    @GetMapping("/group-types/{id}")
    @Timed
    public ResponseEntity<GroupType> getGroupType(@PathVariable Long id) {
        log.debug("REST request to get GroupType : {}", id);
        GroupType groupType = groupTypeService.findOne(id);
        return Optional.ofNullable(groupType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /group-types/:id : delete the "id" groupType.
     *
     * @param id the id of the groupType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/group-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupType(@PathVariable Long id) {
        log.debug("REST request to delete GroupType : {}", id);
        groupTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("groupType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/group-types?query=:query : search for the groupType corresponding
     * to the query.
     *
     * @param query the query of the groupType search 
     * @return the result of the search
     */
    @GetMapping("/_search/group-types")
    @Timed
    public List<GroupType> searchGroupTypes(@RequestParam String query) {
        log.debug("REST request to search GroupTypes for query {}", query);
        return groupTypeService.search(query);
    }


}
