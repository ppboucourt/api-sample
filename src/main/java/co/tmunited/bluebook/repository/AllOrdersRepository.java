package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.AllOrders;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AllOrders entity.
 */
@SuppressWarnings("unused")
public interface AllOrdersRepository extends JpaRepository<AllOrders,Long> {

    List<AllOrders> findAllByDelStatusIsFalse();
}

