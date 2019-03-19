package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypePaymentMethods;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypePaymentMethods entity.
 */
@SuppressWarnings("unused")
public interface TypePaymentMethodsRepository extends JpaRepository<TypePaymentMethods,Long> {

    List<TypePaymentMethods> findAllByDelStatusIsFalse();
}

