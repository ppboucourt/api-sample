package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeFormRules;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeFormRules entity.
 */
@SuppressWarnings("unused")
public interface TypeFormRulesRepository extends JpaRepository<TypeFormRules,Long> {

    List<TypeFormRules> findAllByDelStatusIsFalse();
}

