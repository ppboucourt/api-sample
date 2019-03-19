package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.EmployeeRememberCode;
import co.tmunited.bluebook.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the EmployeeRememberCode entity.
 */
@SuppressWarnings("unused")
public interface EmployeeRememberCodeRepository extends JpaRepository<EmployeeRememberCode,Long> {

    EmployeeRememberCode findByBrowserUuid(String browserUuid);

    EmployeeRememberCode findByBrowserUuidAndCode(String browserUuid, String code);
}

