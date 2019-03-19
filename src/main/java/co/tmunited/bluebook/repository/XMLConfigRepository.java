package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.XMLConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the XMLConfig entity.
 */
@SuppressWarnings("unused")
public interface XMLConfigRepository extends JpaRepository<XMLConfig,Long> {

    List<XMLConfig> findAllByDelStatusIsFalse();

    XMLConfig findFirstByDelStatusIsFalseOrderByCreatedDateDesc();
}

