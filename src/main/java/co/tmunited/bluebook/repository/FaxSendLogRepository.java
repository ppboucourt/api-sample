package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.FaxSendLog;

import co.tmunited.bluebook.domain.vo.FaxSendLogVO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the FaxSendLog entity.
 */
@SuppressWarnings("unused")
public interface FaxSendLogRepository extends JpaRepository<FaxSendLog,Long> {



    List<FaxSendLog> findAllByFinalStatusIsFalse();


    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.FaxSendLogVO(" +
        "faxSendLog.id, " +
        "faxSendLog.sid, " +
        "faxSendLog.sendDate, " +
        "faxSendLog.faxToNumber, " +
        "faxSendLog.faxFromNumber, " +
        "faxSendLog.faxState, " +
        "faxSendLog.pdfUuid, " +
        "faxSendLog.mediaUrl, " +
        "faxSendLog.toName, " +
        "concat(sendBy.firstName,' ', sendBy.lastName), " +
        "faxSendLog.finalStatus, " +
        "concat(patient.firstName,' ', patient.lastName) " +
        ") FROM FaxSendLog faxSendLog " +
        "JOIN faxSendLog.sendBy sendBy " +
        "JOIN faxSendLog.chart chart " +
        "JOIN chart.patient patient " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :facilityId " )
    List<FaxSendLogVO> findAllByFacilityVO(@Param("facilityId") Long facilityId);
}
