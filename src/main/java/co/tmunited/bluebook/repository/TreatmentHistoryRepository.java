package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TreatmentHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TreatmentHistory entity.
 */
@SuppressWarnings("unused")
public interface TreatmentHistoryRepository extends JpaRepository<TreatmentHistory,Long> {

    List<TreatmentHistory> findAllByDelStatusIsFalse();
}

