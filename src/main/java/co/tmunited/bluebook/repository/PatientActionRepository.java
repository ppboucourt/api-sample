package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientAction;
import co.tmunited.bluebook.domain.vo.PatientActionVO;
import co.tmunited.bluebook.domain.vo.PatientMedicationVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PatientAction entity.
 */
@SuppressWarnings("unused")
public interface PatientActionRepository extends JpaRepository<PatientAction,Long> {

    List<PatientAction> findAllByDelStatusIsFalse();

    List<PatientAction> findAllByChartId(Long id);

    @Query("SELECT patientAction FROM PatientAction patientAction " +
        "JOIN patientAction.chart chart " +
        "JOIN chart.facility facility " +
        "JOIN patientAction.signedBy physician " +
        "WHERE " +
        "facility.id = :id AND " +
        "physician.id = :physician AND " +
        "(patientAction.signed = false OR patientAction.signed IS NULL)"
    )
    List<PatientAction> findAllBySignedIsFalseAndDelStatusIsFalseAndSignedById(@Param("id") Long id, @Param("physician") Long physicianId);

    @Query("SELECT patientAction FROM PatientAction patientAction " +
        "JOIN patientAction.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "(patientAction.signed = false OR patientAction.signed IS NULL)"
    )
    List<PatientAction> findAllBySignedIsFalseAndDelStatusIsFalse(@Param("id") Long id);



    @Query("SELECT  NEW co.tmunited.bluebook.domain.vo.PatientActionVO(" +
        "patientAction.id, " +
        "patientActionPre.id, " +
        "patientActionPre.action, " +
        "patientActionPre.asNeeded, " +
        "signedBy.id, " +
        "patientAction.signed, " +
        "signedBy.firstName, " +
        "signedBy.lastName, "  +
        "patientActionPre.staringDate, " +
        "patientActionPre.endDate, " +
        "patientAction.actionStatus )" +

        "FROM PatientActionPre patientActionPre " +
        "JOIN patientActionPre.patientAction patientAction " +
        "JOIN patientActionPre.patientAction.chart chart " +
        "JOIN patientAction.signedBy signedBy " +
        "WHERE " +
        "patientAction.chart.id = :chartId ")
        List<PatientActionVO> getAllPatientActionByChartVO(@Param("chartId") Long chartId);

}
