package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Vendors;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Vendors entity.
 */
@SuppressWarnings("unused")
public interface VendorsRepository extends JpaRepository<Vendors,Long> {

    List<Vendors> findAllByDelStatusIsFalse();
}

