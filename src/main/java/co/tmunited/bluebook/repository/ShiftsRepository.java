package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Shifts;

import co.tmunited.bluebook.domain.vo.ShiftVO;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Shifts entity.
 */
@SuppressWarnings("unused")
public interface ShiftsRepository extends JpaRepository<Shifts, Long> {

    List<Shifts> findAllByDelStatusIsFalse();


    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.ShiftVO(" +
        "shift.id, " +
        "shift.employee.firstName, " +
        "shift.employee.lastName, " +
        "shift.createdDate, " +
        "shift.employee.id" +
        ") FROM Shifts shift " +
        "WHERE " +
        "shift.delStatus = False " +
        "ORDER BY createdDate desc ")
    List<ShiftVO> findAllByDelStatusIsFalseOrderByCreatedDateVO();
}

