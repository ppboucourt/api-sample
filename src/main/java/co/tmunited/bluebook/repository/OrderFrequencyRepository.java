package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.OrderFrequency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderFrequency entity.
 */
@SuppressWarnings("unused")
public interface OrderFrequencyRepository extends JpaRepository<OrderFrequency, Long> {
    List<OrderFrequency> findAllByDaysLessThanAndDaysGreaterThan(int ldays, int gday);
}
