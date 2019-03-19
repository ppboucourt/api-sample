package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Insurance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Insurance entity.
 */
@SuppressWarnings("unused")
public interface InsuranceRepository extends JpaRepository<Insurance,Long> {

    List<Insurance> findAllByDelStatusIsFalse();

    /**
     * Get the insurance belonging to a patient and if it's primary or secundary.
     * @param chartId
     * @param value
     * @return the insurannce filtered by the parameters
     */
    Insurance findByChartIdAndInsuranceOrder(Long chartId, Integer value);

}

