package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Contacts;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Contacts entity.
 */
@SuppressWarnings("unused")
public interface ContactsRepository extends JpaRepository<Contacts,Long> {

    List<Contacts> findAllByDelStatusIsFalse();

    List<Contacts> findAllByDelStatusIsFalseAndChartIdAndTypePatientContactsRelationshipId(Long cid, Long rid);

    List<Contacts> findAllByDelStatusIsFalseAndChartId(Long id);


}

