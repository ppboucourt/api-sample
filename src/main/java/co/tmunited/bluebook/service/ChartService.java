package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.Picture;
import co.tmunited.bluebook.domain.Patient;
import co.tmunited.bluebook.domain.vo.ChartVO;
import co.tmunited.bluebook.domain.vo.PictureVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing Chart.
 */
public interface ChartService {

    /**
     * Save a chart.
     *
     * @param chart the entity to save
     * @return the persisted entity
     */
    Chart save(Chart chart);

    /**
     *  Get all the charts.
     *
     *  @return the list of entities
     */
    List<Chart> findAll();

    /**
     *  Get the "id" chart.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Chart findOne(Long id);

    /**
     *  Delete the "id" chart.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the chart corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Chart> search(String query);

    /**
     *  Get all the charts by facility.
     *
     *  @param id the id of the facility
     *
     *  @return the list of entities filtered by facility
     */
    List<Chart> findAllByFacility(Long id);

    /**
     *  Get all the charts by facility and no has bed.
     *
     *  @param id the id of the facility
     *
     *  @return the list of entities filtered by facility
     */
    List<Chart> findAllByFacilityWithoutBed(Long id, ZonedDateTime date);

    /**
     *  Get all the charts by facility and Current Patients.
     *
     *  @param id the id of the facility
     *
     *  @return the list of entities filtered by facility
     */
    List<Chart> findAllByFacilityWaitingRoomFalseAndDischargeDate(Long id, ZonedDateTime date);


    /**
     *  Get all the chartsVO by facility and Current Patients.
     *
     *  @param id the id of the facility
     *
     *  @return the list of entities filtered by facility
     */
    public List<ChartVO> findAllByFacilityWaitingRoomFalseAndDischargeDateVO(Long id, ZonedDateTime date);

    public List<ChartVO> findAllByFacilityWaitingRoomFalseAndDischargeDateVOForGroupSession(Long id, ZonedDateTime date);

    /**
     *  Get all the charts by facility and is waiting_room = true.
     *
     *  @param id the id of the facility
     *
     *  @return the list of entities filtered by facility
     */
    List<ChartVO> findAllByFacilityWaitingRoomTrue(Long id);

    /**
     *  Get all the charts by facility and is waiting_room = true.
     *
     * @param id from the currentFacility
     *
     * @param date for the discharge date less than today
     *
     * @return the list of chart filtered by facility and the discharge date less than today
     */
    List<ChartVO> findAllArchive(Long id, ZonedDateTime date);

    List<Chart> findAllMedicationsByDay(Long id);

    List<Chart> findAllByPatient(Long facId, Long patId);

    Page<ChartVO> findAllCharts(Long facId, Pageable page, Map<String, Object> mapQuery);

    /**
     * Get the amount of chart until the current year
     * @param id current facility
     * @param start
     * @param end
     * @return
     */
    Long countChartsExistInCurrentYear(Long id, ZonedDateTime start, ZonedDateTime end);


    Picture updatePicture(PictureVO pictureVO);
//    boolean updateAllPicture(Long fId);

    /**
     * Preload forms to a chart
     * @param chart
     */
    void preloadForms(Chart chart);

    Patient findPatientByChart(Long id);


    Chart findByChartToForm(Long id);

    Chart findOneForSchedule(Long id);
}
