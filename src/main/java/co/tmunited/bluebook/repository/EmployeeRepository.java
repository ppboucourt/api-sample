package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Employee;

import co.tmunited.bluebook.domain.TypeEmployee;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Employee entity.
 */
@SuppressWarnings("unused")
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findAllByDelStatusIsFalse();

    /**
     * Get an Employee by User Id.
     * @param id User Id
     * @return Employee or null
     */
    Optional<Employee> findOneByUserId(Long id);


    /**
     * Get an Employee by User Id.
     * @param id Employee Id
     * @return Employee or null
     */
    Optional<Employee> findOneByIdAndDelStatusIsFalse(Long id);

    /**
     * Get a list of Employee filtered by facility and Type Employee = Therapist(2)
     * @param typeEmployeeId
     * @return
     */
    List<Employee> findAllByTypeEmployeeIdAndDelStatusIsFalse(Long typeEmployeeId);

    Optional<Employee> findOneByUserLogin(String login);

    List<Employee> findAllByTypeEmployeeAndDelStatusIsFalse(TypeEmployee typeEmployee);
}

