package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Employee;
import co.tmunited.bluebook.web.rest.util.BooleanWrapper;
import co.tmunited.bluebook.web.rest.util.LongWrapper;
import co.tmunited.bluebook.web.rest.util.SmsResponseWrapper;
import co.tmunited.bluebook.web.rest.util.VerifyCodeWrapper;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Employee.
 */
public interface EmployeeService {

    /**
     * Save a employee.
     *
     * @param employee the entity to save
     * @return the persisted entity
     */
    Employee save(Employee employee);

    /**
     *  Get all the employees.
     *
     *  @return the list of entities
     */
    List<Employee> findAll();

    /**
     *  Get the "id" employee.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Employee findOne(Long id);

    /**
     *  Delete the "id" employee.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the employee corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Employee> search(String query);

    /**
     * Search the employee who are Therapists and belong to a one facility
     *
     * @param typeEmployeeId
     * @return
     */
    List<Employee> findAllByTypeEmployee(Long typeEmployeeId);

    /**
     * Search one employee who have user login
     *
     * @param login
     * @return
     */
    Optional<Employee> findOneByUserLogin(String login);


    /**
     * Search one employee who have user login
     *
     * @param id
     * @param pin
     * @return True or False
     */
    Boolean employeeIsValidPin(Long id, String pin);

    List<Employee> findAllPhysicianEmployees();

    LongWrapper isTwoFactorNeeded(String browserUuid);

    SmsResponseWrapper sendSmsCode(String browserUuid);

    BooleanWrapper rememberMe(BooleanWrapper booleanWrapper);

    BooleanWrapper verifyCode(VerifyCodeWrapper verifyCodeWrapper);
}
