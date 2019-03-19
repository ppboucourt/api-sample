package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.service.UserService;
import co.tmunited.bluebook.web.rest.util.*;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Employee;
import co.tmunited.bluebook.domain.User;
import co.tmunited.bluebook.service.EmployeeService;
import co.tmunited.bluebook.service.UserService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Employee.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    @Inject
    private EmployeeService employeeService;

    @Inject
    private UserService userService;

    /**
     * POST  /employees : Create a new employee.
     *
     * @param employee the employee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employee, or with status 400 (Bad Request) if the employee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employees")
    @Timed
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employee);

        Optional<User> user =  userService.getUserWithAuthoritiesByLogin(employee.getUser().getLogin());

        if (employee.getId() != null ) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("employee", "idexists", "A new employee cannot already have an ID")).body(null);
        }

        if (user.isPresent() ) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user", "loginexists", "Username already exists, please choose a different one")).body(null);
        }

        Employee result = employeeService.save(employee);
        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employees : Updates an existing employee.
     *
     * @param employee the employee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employee,
     * or with status 400 (Bad Request) if the employee is not valid,
     * or with status 500 (Internal Server Error) if the employee couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employees")
    @Timed
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to update Employee : {}", employee);
        if (employee.getId() == null) {
            return createEmployee(employee);
        }
        Employee result = employeeService.save(employee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employee", employee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employees : get all the employees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employees in body
     */
    @GetMapping("/employees")
    @Timed
    public List<Employee> getAllEmployees() {
        log.debug("REST request to get all Employees");
        return employeeService.findAll();
    }

    /**
     * GET  /employees/:id : get the "id" employee.
     *
     * @param id the id of the employee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employee, or with status 404 (Not Found)
     */
    @GetMapping("/employees/{id}")
    @Timed
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        Employee employee = employeeService.findOne(id);
        return Optional.ofNullable(employee)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employees/:id : delete the "id" employee.
     *
     * @param id the id of the employee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employees/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employee", id.toString())).build();
    }

    /**
     * SEARCH  /_search/employees?query=:query : search for the employee corresponding
     * to the query.
     *
     * @param query the query of the employee search
     * @return the result of the search
     */
    @GetMapping("/_search/employees")
    @Timed
    public List<Employee> searchEmployees(@RequestParam String query) {
        log.debug("REST request to search Employees for query {}", query);
        return employeeService.search(query);
    }

    /**
     * GET  /employeeAccount : get the current Employee Account.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body,
     * or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/employeeAccount")
    @Timed
    public ResponseEntity<Employee> getEmployeeAccount() {
        log.debug("REST request to get authenticated Employee Account");
        Employee employee = userService.getEmployeeWithAuthorities();

        return Optional.ofNullable(employee)
            .map(tmpEmployee -> new ResponseEntity<>(tmpEmployee, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET employee who are therapists and belong to a one facility
     *
     * @param teid TypeEmployeeId(Therapist = 2)
     * @return
     */
    @GetMapping("/therapists/{teid}")
    public List<Employee> getAllTherapists(@PathVariable Long teid){
        log.debug("REST request to get all Employees by TypeEmployee");
        return employeeService.findAllByTypeEmployee(teid);
    }

    /**
     * GET  /employeeBylogin/:login : get the "id" employee.
     *
     * @param login the login of the employee user to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employee, or with status 404 (Not Found)
     */
    @GetMapping("/employeeByLogin/{login}")
    @Timed
    public ResponseEntity<Employee> getEmployeeByLogin(@PathVariable String login) {

        log.debug("REST request to get Employee : {}", login);
        Optional<Employee> employee = employeeService.findOneByUserLogin(login);

        return employee
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * TEST employee pin
     *
     * @param id EmployeeId(id = 2)
     *
     * @param pin EmployeePin(pin = 1234)
     * @return
     */
    @GetMapping("/employeeIsValidPin/{id}/{pin}")
    public ResponseEntity<BooleanWrapper> employeeIsValidPin(@PathVariable Long id, @PathVariable String pin){
        log.debug("REST request to valid pin: {}", pin);
        Boolean  isValid = employeeService.employeeIsValidPin(id, pin);
        BooleanWrapper booleanWrapper = new BooleanWrapper();
        booleanWrapper.setValue(isValid);

        return  new ResponseEntity<>(booleanWrapper,
                HttpStatus.OK);
    }


    @GetMapping("/employees/physicians")
    @Timed
    public List<Employee> getPhysicianEmployees() {
        return employeeService.findAllPhysicianEmployees();
    }


    /**
     * GET  /isTwoFactorNeeded/{browserUuid}
     *
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/isTwoFactorNeeded/{browserUuid}")
    @Timed
    public ResponseEntity<LongWrapper> isTwoFactorNeeded(@PathVariable String browserUuid) {
        log.debug("REST request to get isTwoFactorNeeded...");
        LongWrapper longWrapper = employeeService.isTwoFactorNeeded(browserUuid);

        return  new ResponseEntity<>(longWrapper,
            HttpStatus.OK);

    }

    /**
     * GET  /sendSmsCode/
     *
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/sendSmsCode/{browserUuid}")
    @Timed
    public ResponseEntity<SmsResponseWrapper> sendSmsCode(@PathVariable String browserUuid) {
        log.debug("REST request to get sendSmsCode...");
        SmsResponseWrapper smsResponseWrapper = employeeService.sendSmsCode(browserUuid);

        return  new ResponseEntity<>(smsResponseWrapper,
            HttpStatus.OK);

    }

    /**
     * GET  /rememberMe/
     *
     * @return the ResponseEntity with status 200 (OK) and with body the "BooleanWrapper" user
     */
    @PostMapping("/rememberMe")
    @Timed
    public ResponseEntity<BooleanWrapper> rememberMe(@RequestBody BooleanWrapper booleanWrapper, HttpServletRequest request) {
        log.debug("REST request to set rememberMe...");
        BooleanWrapper  smsResponseWrapper = employeeService.rememberMe(booleanWrapper);

        return  new ResponseEntity<>(smsResponseWrapper,
            HttpStatus.OK);

    }

    @PostMapping("/verifyCode")
    @Timed
    public ResponseEntity<BooleanWrapper> verifyCode(@RequestBody VerifyCodeWrapper verifyCodeWrapper, HttpServletRequest request) {
        log.debug("REST request to set verifyCode...");
        BooleanWrapper  verifyWrapper = employeeService.verifyCode(verifyCodeWrapper);

        return  new ResponseEntity<>(verifyWrapper,
            HttpStatus.OK);

    }

}
