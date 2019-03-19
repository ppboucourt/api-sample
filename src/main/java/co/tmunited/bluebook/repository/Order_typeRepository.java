package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Order_type;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Order_type entity.
 */
@SuppressWarnings("unused")
public interface Order_typeRepository extends JpaRepository<Order_type,Long> {

    List<Order_type> findAllByDelStatusIsFalse();
}

