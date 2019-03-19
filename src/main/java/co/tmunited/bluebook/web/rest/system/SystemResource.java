package co.tmunited.bluebook.web.rest.system;

import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.service.*;
import co.tmunited.bluebook.service.dto.FileDTO;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Machine.
 */
@RestController
@RequestMapping("/system")
public class SystemResource {

    private final Logger log = LoggerFactory.getLogger(SystemResource.class);

    @Inject
    private ChartToFormService chartToFormService;

    @Inject
    private EvaluationService evaluationService;

    @Inject
    private ShiftsService shiftService;

    @Inject
    private ChartService chartService;

    @Inject
    private CorporationService corporationService;

    @Inject
    private PatientOrderItemService patientOrderItemService;

    /**
     * POST  /process-patient-result : Process a patientResult.
     *
     * @param fileDTO the patientResult to process
     * @return the ResponseEntity with status 201 (Created) and with body the new file, or with status 400 (Bad Request) if the patientResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     *
     */
    @PostMapping("/consent/attach-file")
    @Timed
    public ResponseEntity<File> attachConsentFile(@RequestBody FileDTO fileDTO) throws URISyntaxException {
        log.debug("REST request to process an attach file for consents : {}", fileDTO);
        if (fileDTO.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientResult", "idexists", "A new patientResult already have an ID")).body(null);
        }

        File file = chartToFormService.attachConsentFile(fileDTO);

        return Optional.ofNullable(file)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * POST  /process-patient-result : Process a patientResult.
     *
     * @param fileDTO the patientResult to process
     * @return the ResponseEntity with status 201 (Created) and with body the new file, or with status 400 (Bad Request) if the patientResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     *
     */
    @PostMapping("/evaluation/attach-file")
    @Timed
    public ResponseEntity<File> attachEvaluationFile(@RequestBody FileDTO fileDTO) throws URISyntaxException {
        log.debug("REST request to process an attach file for consents : {}", fileDTO);
        if (fileDTO.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientResult", "idexists", "A new patientResult already have an ID")).body(null);
        }

        File file = evaluationService.attachEvaluationFile(fileDTO);

        return Optional.ofNullable(file)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * POST  /shift/attach-file : Attach file to shifts.
     *
     * @param fileDTO the patientResult to process
     * @return the ResponseEntity with status 201 (Created) and with body the new file, or with status 400 (Bad Request) if the patientResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shift/attach-file")
    @Timed
    public ResponseEntity<File> attachShiftFile(@RequestBody FileDTO fileDTO) throws URISyntaxException {
        log.debug("REST request to process an attach file for shift : {}", fileDTO);
        if (fileDTO.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("shiftAttachFile", "nullId", "To store the attachment you need to provide an ID.")).body(null);
        }

        File file = shiftService.attachFile(fileDTO);

        return Optional.ofNullable(file)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /charts/:id : get the "id" chart.
     *
     * @param id the id of the chart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chart, or with status 404 (Not Found)
     */
    @GetMapping("/charts/{id}")
    @Timed
    public ResponseEntity<Chart> getChart(@PathVariable Long id) {
        log.debug("REST request to get Chart : {}", id);
        Chart chart = new Chart();
        try {
            chart = chartService.findOneForSchedule(id);
        } catch (Exception e){
            log.info(e.toString());
            e.printStackTrace();
        }
        return Optional.ofNullable(chart)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /corporations
     *
     * @return the ResponseEntity with status 200 (OK) and with body the corporation, or with status 404 (Not Found)
     */
    @GetMapping("/corporation")
    @Timed
    public ResponseEntity<Corporation> getCorporation() {
        log.debug("REST request to get Corporation");
        Corporation corporation = new Corporation();
        try {
            List<Corporation> list = corporationService.findAll();
            corporation = list.get(0);//Based on the Logic we always going to have one Corporation
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        }
        return Optional.ofNullable(corporation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/patient-orders-items-to-process")
    @Timed
    public List<PatientOrderItem> getPatientOrderItemsToProcess() {
        log.debug("REST request to get Order Items to process.");
        List<PatientOrderItem> patientOrderItems = new ArrayList<>();
        patientOrderItems = patientOrderItemService.getPatientOrderItemsToProcess();

        return patientOrderItems;
    }


}
