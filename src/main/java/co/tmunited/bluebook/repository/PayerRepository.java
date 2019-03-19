package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Payer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Payer entity.
 */
@SuppressWarnings("unused")
public interface PayerRepository extends JpaRepository<Payer,Long> {

    List<Payer> findAllByDelStatusIsFalse();
}

