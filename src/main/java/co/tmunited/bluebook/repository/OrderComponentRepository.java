package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.OrderComponent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderComponent entity.
 */
@SuppressWarnings("unused")
public interface OrderComponentRepository extends JpaRepository<OrderComponent,Long> {

    List<OrderComponent> findAllByDelStatusIsFalse();

    /**
     *
     * @param allOrdersId
     * @return List filtered by allOrdersId
     *
     */
    List<OrderComponent> findAllByDelStatusIsFalseAndAllOrders(Long allOrdersId);
}

