package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientMedication;
import co.tmunited.bluebook.domain.vo.ChartVO;
import co.tmunited.bluebook.domain.vo.PatientMedicationVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the PatientMedication entity.
 */
@SuppressWarnings("unused")
public interface PatientMedicationRepository extends JpaRepository<PatientMedication,Long> {

    List<PatientMedication> findAllByDelStatusIsFalse();

    List<PatientMedication> findAllByChartId(Long id);

    @Query("SELECT  NEW co.tmunited.bluebook.domain.vo.PatientMedicationVO(" +
        "patientMedicationPres.id, " +
        "medications.medication, " +
        "patientMedicationPres.asNeeded, " +
        "patientMedicationPres.staringDate, " +
        "patientMedicationPres.endDate, " +
        "signedBy.firstName, " +
        "signedBy.lastName, "  +
        "patientMedication.medicationStatus, " +
        "patientMedication.signed, " +
        "signedBy.id ) " +
        "FROM PatientMedicationPres patientMedicationPres " +
        "JOIN patientMedicationPres.patientMedication patientMedication " +
        "JOIN patientMedicationPres.patientMedication.chart chart " +
        "JOIN patientMedicationPres.medications medications " +
        "JOIN patientMedication.signedBy signedBy " +
        "WHERE " +
        "patientMedication.chart.id = :chartId ")
    List<PatientMedicationVO> findAllByChartIdVO(@Param("chartId") Long chartId);



    @Query("SELECT patientMedication FROM PatientMedication patientMedication " +
        "JOIN patientMedication.chart chart " +
        "JOIN chart.facility facility " +
        "JOIN patientMedication.signedBy physician " +
        "WHERE " +
        "facility.id = :id AND " +
        "physician.id = :physician AND " +
        "(patientMedication.signed = false OR patientMedication.signed IS NULL)"
    )
    List<PatientMedication> findAllBySignedIsFalseAndDelStatusIsFalseAndSignedById(@Param("id") Long id, @Param("physician") Long physicianId);

    @Query("SELECT patientMedication FROM PatientMedication patientMedication " +
        "JOIN patientMedication.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "(patientMedication.signed = false OR patientMedication.signed IS NULL)"
    )
    List<PatientMedication> findAllBySignedIsFalseAndDelStatusIsFalse(@Param("id") Long id);


    @Query("SELECT DISTINCT pmedication FROM PatientMedicationTake patientMedicationTake " +
           "JOIN patientMedicationTake.patientMedicationPres prescr " +
           "JOIN prescr.patientMedication pmedication  " +
           "WHERE " +
           "patientMedicationTake.id = :id  "
    )
    PatientMedication findPatientMedicationByTake(@Param("id") Long id);

    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.PatientMedicationVO(" +
        "patientMedication.id, " +
        "CONCAT(physician.firstName, ' ', physician.lastName), " +
        "physician.npiNumber, " +
        "physician.deaNumber, " +
        "physician.title, " +
        "CONCAT(employee.firstName, ' ', employee.lastName), " +
        "employee.title, " +
        "patientMedication.createdDate, " +
        "CONCAT(patient.firstName, ' ', patient.lastName), " +
        "patient.dateBirth, " +
        "chart.phone, " +
        "patient.sex, " +
        "CONCAT(chart.address, ', ', chart.city, ', ', chart.state, ' ', chart.zip), " +
        "chart.mrNo, " +
        "chart.id, " +
        "facility.name, " +
        "facility.phone, " +
        "facility.street, " +
        "facility.city, " +
        "facility.state, " +
        "facility.zip, " +
        "facility.county, " +
        "facility.website, " +
        "medications.medication, " +
        "pMedicationPres.dose, " +
        "pMedicationPres.dosegeForm, " +
        "pMedicationPres.amountToDispense, " +
        "patientMedication.refills, " +
        "via.name, " +
        "route.name, " +
        "patientMedication.justification, " +
        "pMedicationPres.staringDate, " +
        "pMedicationPres.endDate, " +
        "pMedicationPres.hours, " +
        "insuranceCarrier.name, " +
        "insurance.policyNumber, " +
        "insurance.groupNumber, " +
        "insurance.groupName, " +
        "insurance.planNumber, " +
        "insurance.dob, " +
        "insurance.phone, " +
        "insuranceType.name, " +
        "insuranceRelation.name, " +
        "CONCAT(insurance.firstName, ' ', insurance.lastName) " +
        ") " +
        "FROM PatientMedication patientMedication " +
        "LEFT JOIN patientMedication.route route " +
        "LEFT JOIN patientMedication.via via " +
        "LEFT JOIN patientMedication.patientMedicationPress pMedicationPres " +
        "LEFT JOIN pMedicationPres.medications medications " +
        "JOIN patientMedication.employee employee " +
        "JOIN patientMedication.chart chart " +
        "JOIN chart.patient patient " +
        "LEFT JOIN chart.insurances insurance " +
        "LEFT JOIN insurance.insuranceType insuranceType " +
        "LEFT JOIN insurance.insuranceRelation insuranceRelation " +
        "LEFT JOIN insurance.insuranceCarrier insuranceCarrier " +
        "JOIN chart.facility facility " +
        "JOIN patientMedication.employee employee " +
        "JOIN patientMedication.signedBy physician " +
        "WHERE " +
        "patientMedication.id = :id AND " +
        "chart.delStatus = False"
    )
    PatientMedicationVO findPatientMedicationToGeneratePDF(@Param("id") Long id);



    @Query("SELECT DISTINCT patientMedication FROM PatientMedication patientMedication " +
        "JOIN patientMedication.patientMedicationPress patientMedicationPress " +
        "WHERE " +
        "patientMedicationPress.id = :id ")
    PatientMedication findPatientMedicationByPress(@Param("id") Long id);
}
