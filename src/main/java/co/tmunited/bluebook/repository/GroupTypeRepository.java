package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.GroupType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GroupType entity.
 */
@SuppressWarnings("unused")
public interface GroupTypeRepository extends JpaRepository<GroupType,Long> {

    List<GroupType> findAllByDelStatusIsFalse();
}

