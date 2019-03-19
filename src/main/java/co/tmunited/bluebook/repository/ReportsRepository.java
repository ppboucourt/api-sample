package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Reports;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Reports entity.
 */
@SuppressWarnings("unused")
public interface ReportsRepository extends JpaRepository<Reports,Long> {

    List<Reports> findAllByDelStatusIsFalse();
}

