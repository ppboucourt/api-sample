package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.LabCompendium;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LabCompendium entity.
 */
@SuppressWarnings("unused")
public interface LabCompendiumRepository extends JpaRepository<LabCompendium,Long> {

    List<LabCompendium> findAllByDelStatusIsFalse();
}

