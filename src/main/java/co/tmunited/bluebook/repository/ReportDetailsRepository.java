package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ReportDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ReportDetails entity.
 */
@SuppressWarnings("unused")
public interface ReportDetailsRepository extends JpaRepository<ReportDetails,Long> {

    List<ReportDetails> findAllByDelStatusIsFalse();
}

