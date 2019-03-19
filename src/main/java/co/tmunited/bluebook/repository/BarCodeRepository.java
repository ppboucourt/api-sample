package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.BarCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CareTeam entity.
 */
@SuppressWarnings("unused")
public interface BarCodeRepository extends JpaRepository<BarCode, Long> {
}

