package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ChartToForm;
import co.tmunited.bluebook.domain.enumeration.FormStatus;

import co.tmunited.bluebook.domain.vo.ChartVO;
import co.tmunited.bluebook.domain.vo.ChartToFormVO;

import co.tmunited.bluebook.domain.vo.FormVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the ChartToForm entity.
 */
@SuppressWarnings("unused")
public interface ChartToFormRepository extends JpaRepository<ChartToForm,Long> {

    @Query(" SELECT chartToForm FROM ChartToForm chartToForm " +
        " JOIN FETCH chartToForm.chart " +
        " WHERE chartToForm.id = :id AND " +
        " chartToForm.delStatus = false ")
    ChartToForm findOne(@Param("id") Long id);

    List<ChartToForm> findAllByDelStatusIsFalse();

    List<ChartToForm> findAllByDelStatusIsFalseAndChartIdOrderByIdAsc(Long id);

    @Query("SELECT chartToForm FROM ChartToForm chartToForm " +
        "JOIN FETCH chartToForm.chart chart " +
        "WHERE " +
        "chartToForm.chart.id = chart.id AND " +
        "chart.dischargeDate = :date AND " +
        "chart.waitingRoom = false AND " +
        "chart.delStatus = false")
    List<ChartToForm> findChartToFormOfCurrentPatientInCorporation(@Param("date") ZonedDateTime date);

    @Query("SELECT chartToForm FROM ChartToForm chartToForm " +
        "JOIN FETCH chartToForm.chart chart " +
        "WHERE " +
        "chartToForm.chart.id = chart.id AND " +
        "(chartToForm.status = :statusOne or chartToForm.status = :statusTwo)  AND " +
        "chart.facility.id = :fId AND " +
        "chart.delStatus = false AND chartToForm.delStatus = false")
    List<ChartToForm> findAllChartToFormUnsigned(@Param("fId") Long fId, @Param("statusOne") FormStatus statusOne, @Param("statusTwo") FormStatus statusTwo);




//    @Query("SELECT chartToForm FROM ChartToForm chartToForm " +
//        "JOIN FETCH chartToForm.chart chart " +
//        "WHERE " +
//        "chartToForm.chart.id = chart.id AND " +
//        "(chartToForm.status = :statusOne or chartToForm.status = :statusTwo)  AND " +
//        "chart.facility.id = :fId AND " +
//        "chart.delStatus = false AND chartToForm.delStatus = false")

    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.ChartVO(" +
        "chart.id, " +
        "chart.patient.firstName, " +
        "chart.patient.lastName, " +
        "chart.mrNo, " +
        "' ', " +
        "' ', " +
        "'', " +
        "chart.facility.name, " +
        "'', " +
        "'', " +
        "chart.admissionDate, " +
        "chart.dischargeDate, " +
        "chartToForms.id, " +
        "chartToForms.name " +
        ") FROM Chart chart " +
        "JOIN chart.facility facility " +
       // "JOIN chart.chartToForms chartToForms " +
        // "LEFT JOIN chart.bed bed " +
        // "LEFT JOIN chart.typePatientPrograms typePatientPrograms " +
        "LEFT JOIN  chart.chartToForms chartToForms " +
        "WHERE " +
        "facility.id = :fId AND " +
        "(chartToForms.status = :statusOne or chartToForms.status = :statusTwo)  AND " +
        "chart.facility.id = :fId AND " +
        "chart.delStatus = false AND chartToForms.delStatus = false")

    List<ChartVO> findAllChartToFormUnsignedVO(@Param("fId") Long fId, @Param("statusOne") FormStatus statusOne, @Param("statusTwo") FormStatus statusTwo);

    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.ChartToFormVO(" +
        "chartToForm.id, " +
        "chartToForm.status, " +
        "chartToForm.jsonData, " +
        "chartToForm.name, " +
        "chartToForm.htmlData, " +
        "chartToForm.formId, " +
        "chartToForm.patientSignatureRequired, " +
        "chartToForm.guarantorSignatureRequired, " +
        "chartToForm.allowAttachment, " +
        "chartToForm.allowRevocation, " +
        "chartToForm.expiresDays, " +
        "chartToForm.onlyOne, " +
        "chartToForm.expire, " +
        "chartToForm.typePatientProcessId, " +
        "chartToForm.contentHtml, " +
        "chartToForm.loadAutomatic, " +
        "chartToForm.signaturePatient, " +
        "chartToForm.signatureGuarantor, " +
        "chartToForm.revocationPatient, " +
        "chartToForm.revocationGuarantor, " +
        "chartToForm.guarantor.fullName " +
        ") FROM ChartToForm chartToForm " +
        "LEFT JOIN chartToForm.guarantor contact " +
        "LEFT JOIN chartToForm.signaturePatient sigpatient " +
        "LEFT JOIN chartToForm.signatureGuarantor sigguarantor " +
        "LEFT JOIN chartToForm.revocationPatient revpatient " +
        "LEFT JOIN chartToForm.revocationGuarantor revguarantor " +
        "WHERE " +
        "chartToForm.id = :id AND " +
        "chartToForm.delStatus = false " )
    ChartToFormVO findOneVO(@Param("id") Long id);

    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.ChartToFormVO(" +
        "chartToForm.id, " +
        "chartToForm.status, " +
        "chartToForm.jsonData, " +
        "chartToForm.name, " +
        "chartToForm.htmlData, " +
        "chartToForm.formId, " +
        "chartToForm.patientSignatureRequired, " +
        "chartToForm.guarantorSignatureRequired, " +
        "chartToForm.allowAttachment, " +
        "chartToForm.allowRevocation, " +
        "chartToForm.expiresDays, " +
        "chartToForm.onlyOne, " +
        "chartToForm.expire, " +
        "chartToForm.typePatientProcessId, " +
        "chartToForm.contentHtml, " +
        "chartToForm.loadAutomatic, " +
        "chartToForm.signaturePatient, " +
        "chartToForm.signatureGuarantor, " +
        "chartToForm.revocationPatient, " +
        "chartToForm.revocationGuarantor, " +
        "chartToForm.guarantor.fullName, " +
        "chartToForm.chart " +
        ") FROM ChartToForm chartToForm " +
        "LEFT JOIN chartToForm.guarantor contact " +
        "LEFT JOIN chartToForm.chart chart " +
        "LEFT JOIN chartToForm.signaturePatient sigpatient " +
        "LEFT JOIN chartToForm.signatureGuarantor sigguarantor " +
        "LEFT JOIN chartToForm.revocationPatient revpatient " +
        "LEFT JOIN chartToForm.revocationGuarantor revguarantor " +
        "WHERE " +
        "chartToForm.id = :id AND " +
        "chartToForm.delStatus = false " )
    ChartToFormVO findOneVOChart(@Param("id") Long id);



}

