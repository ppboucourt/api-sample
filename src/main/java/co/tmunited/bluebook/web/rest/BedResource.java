package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.service.ChartService;
import co.tmunited.bluebook.domain.vo.BedBuildingVO;
import co.tmunited.bluebook.service.dto.BedDTO;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Bed;
import co.tmunited.bluebook.service.BedService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Bed.
 */
@RestController
@RequestMapping("/api")
public class BedResource {

    private final Logger log = LoggerFactory.getLogger(BedResource.class);

    @Inject
    private BedService bedService;

    @Inject
    private ChartService chartService;

    /**
     * POST  /beds : Create a new bed.
     *
     * @param bed the bed to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bed, or with status 400 (Bad Request) if the bed has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/beds")
    @Timed
    public ResponseEntity<Bed> createBed(@Valid @RequestBody Bed bed) throws URISyntaxException {
        log.debug("REST request to save Bed : {}", bed);
        if (bed.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("bed", "idexists", "A new bed cannot already have an ID")).body(null);
        }
        Bed result = bedService.save(bed);
        return ResponseEntity.created(new URI("/api/beds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bed", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beds : Updates an existing bed.
     *
     * @param bed the bed to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bed,
     * or with status 400 (Bad Request) if the bed is not valid,
     * or with status 500 (Internal Server Error) if the bed couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/beds")
    @Timed
    public ResponseEntity<Bed> updateBed(@Valid @RequestBody Bed bed) throws URISyntaxException {
        log.debug("REST request to update Bed : {}", bed);
        if (bed.getId() == null) {
            return createBed(bed);
        }
        Bed result = bedService.save(bed);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bed", bed.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beds : get all the beds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of beds in body
     */
    @GetMapping("/beds")
    @Timed
    public List<Bed> getAllBeds() {
        log.debug("REST request to get all Beds");
        return bedService.findAll();
    }

    /**
     * GET  /beds/:id : get the "id" bed.
     *
     * @param id the id of the bed to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bed, or with status 404 (Not Found)
     */
    @GetMapping("/beds/{id}")
    @Timed
    public ResponseEntity<Bed> getBed(@PathVariable Long id) {
        log.debug("REST request to get Bed : {}", id);
        Bed bed = bedService.findOne(id);
        return Optional.ofNullable(bed)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /beds/:id : delete the "id" bed.
     *
     * @param id the id of the bed to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/beds/{id}")
    @Timed
    public ResponseEntity<Void> deleteBed(@PathVariable Long id) {
        log.debug("REST request to delete Bed : {}", id);
        bedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bed", id.toString())).build();
    }

    /**
     * SEARCH  /_search/beds?query=:query : search for the bed corresponding
     * to the query.
     *
     * @param query the query of the bed search
     * @return the result of the search
     */
    @GetMapping("/_search/beds")
    @Timed
    public List<Bed> searchBeds(@RequestParam String query) {
        log.debug("REST request to search Beds for query {}", query);
        return bedService.search(query);
    }

    /**
     * GET  /beds : get all the beds by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of beds in body
     */
    @GetMapping("/beds-facility/{id}")
    @Timed
    public List<Bed> getAllBedsByFacility(@PathVariable Long id) {
        log.debug("REST request to get all Beds by Facility");
        return bedService.findAllByFacility(id);
    }

    /**
     * GET  /beds-chart/:id : get the "id" bed.
     *
     * @param id the id of the bed to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bed, or with status 404 (Not Found)
     */
//    @GetMapping("/beds-chart/{id}")
//    @Timed
//    public ResponseEntity<BedDTO> getBedAndChart(@PathVariable Long id) {
//        log.debug("REST request to get Bed and it's Chart : {}", id);
//        BedDTO bed = bedService.findOneWithChart(id);
//        return Optional.ofNullable(bed)
//            .map(result -> new ResponseEntity<>(
//                result,
//                HttpStatus.OK))
//            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @GetMapping("/free-beds/{id}/{actualChart}")
    @Timed
    public List<Bed> getAllFreeBeds(@PathVariable Long id, @PathVariable Long actualChart) {
        log.debug("REST request to get all free Beds by Facility");
        return bedService.findAllFreeBeds(id, actualChart);
    }

    @GetMapping("/beds/building/{id}")
    public List<BedBuildingVO> findBedsByBuilding(@PathVariable Long id) {
        log.debug("REST request to get all beds by Building {}", id);
        return bedService.findAllByBuildingId(id);
    }

    @PostMapping("/beds/ids")
    @Timed
    public List<Bed> findBedByListIds(@RequestBody CollectedBody collectedBody) throws URISyntaxException {
        return bedService.findBedByListIds(collectedBody);
    }

}
