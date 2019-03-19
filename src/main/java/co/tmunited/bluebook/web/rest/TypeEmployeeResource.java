package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeEmployee;
import co.tmunited.bluebook.service.TypeEmployeeService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TypeEmployee.
 */
@RestController
@RequestMapping("/api")
public class TypeEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(TypeEmployeeResource.class);
        
    @Inject
    private TypeEmployeeService typeEmployeeService;

    /**
     * POST  /type-employees : Create a new typeEmployee.
     *
     * @param typeEmployee the typeEmployee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeEmployee, or with status 400 (Bad Request) if the typeEmployee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-employees")
    @Timed
    public ResponseEntity<TypeEmployee> createTypeEmployee(@Valid @RequestBody TypeEmployee typeEmployee) throws URISyntaxException {
        log.debug("REST request to save TypeEmployee : {}", typeEmployee);
        if (typeEmployee.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeEmployee", "idexists", "A new typeEmployee cannot already have an ID")).body(null);
        }
        TypeEmployee result = typeEmployeeService.save(typeEmployee);
        return ResponseEntity.created(new URI("/api/type-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeEmployee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-employees : Updates an existing typeEmployee.
     *
     * @param typeEmployee the typeEmployee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeEmployee,
     * or with status 400 (Bad Request) if the typeEmployee is not valid,
     * or with status 500 (Internal Server Error) if the typeEmployee couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-employees")
    @Timed
    public ResponseEntity<TypeEmployee> updateTypeEmployee(@Valid @RequestBody TypeEmployee typeEmployee) throws URISyntaxException {
        log.debug("REST request to update TypeEmployee : {}", typeEmployee);
        if (typeEmployee.getId() == null) {
            return createTypeEmployee(typeEmployee);
        }
        TypeEmployee result = typeEmployeeService.save(typeEmployee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeEmployee", typeEmployee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-employees : get all the typeEmployees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeEmployees in body
     */
    @GetMapping("/type-employees")
    @Timed
    public List<TypeEmployee> getAllTypeEmployees() {
        log.debug("REST request to get all TypeEmployees");
        return typeEmployeeService.findAll();
    }

    /**
     * GET  /type-employees/:id : get the "id" typeEmployee.
     *
     * @param id the id of the typeEmployee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeEmployee, or with status 404 (Not Found)
     */
    @GetMapping("/type-employees/{id}")
    @Timed
    public ResponseEntity<TypeEmployee> getTypeEmployee(@PathVariable Long id) {
        log.debug("REST request to get TypeEmployee : {}", id);
        TypeEmployee typeEmployee = typeEmployeeService.findOne(id);
        return Optional.ofNullable(typeEmployee)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-employees/:id : delete the "id" typeEmployee.
     *
     * @param id the id of the typeEmployee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-employees/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeEmployee(@PathVariable Long id) {
        log.debug("REST request to delete TypeEmployee : {}", id);
        typeEmployeeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeEmployee", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-employees?query=:query : search for the typeEmployee corresponding
     * to the query.
     *
     * @param query the query of the typeEmployee search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-employees")
    @Timed
    public List<TypeEmployee> searchTypeEmployees(@RequestParam String query) {
        log.debug("REST request to search TypeEmployees for query {}", query);
        return typeEmployeeService.search(query);
    }


}
