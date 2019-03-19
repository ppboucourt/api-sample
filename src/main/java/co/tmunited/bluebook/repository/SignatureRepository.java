package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Signature;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Signature entity.
 */
@SuppressWarnings("unused")
public interface SignatureRepository extends JpaRepository<Signature,Long> {

    List<Signature> findAllByDelStatusIsFalse();
}

