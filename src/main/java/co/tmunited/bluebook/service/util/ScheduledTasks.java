package co.tmunited.bluebook.service.util;


import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.ChartToForm;
import co.tmunited.bluebook.domain.enumeration.FormStatus;
import co.tmunited.bluebook.domain.util.FormJson;
import co.tmunited.bluebook.repository.ChartRepository;
import co.tmunited.bluebook.repository.ChartToFormRepository;
import co.tmunited.bluebook.service.ChartService;
import co.tmunited.bluebook.service.ChartToFormService;
import co.tmunited.bluebook.web.rest.ChartResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by PpTMUnited on 5/8/2017.
 */
@SpringBootApplication
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Inject
//    private ChartToFormRepository chartToFormRepository;
//
//    @Inject
//    private ChartRepository chartRepository;
    //0 0 8-10 * * *
//    @Scheduled(cron = "*/10 * * * * *")
//    public void checkExpiresConsents() {
//        log.info("The time is now {}", dateFormat.format(new Date()));
//
//        ZonedDateTime now = ZonedDateTime.now();
//        List<ChartToForm> currentChartToForms = chartToFormRepository.findChartToFormOfCurrentPatientInCorporation(now);
//
//        currentChartToForms.stream().map(cForm -> {
//
//            ObjectMapper mapper = new ObjectMapper();
//            FormJson formJson = new FormJson();
//
//            try {
//                formJson = mapper.readValue(cForm.getJsonData(), FormJson.class);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (formJson.getExpires()) {
//                LocalDate expireDate = cForm.getCreatedDate().toLocalDate().plusDays(formJson.getExpiresDays());
//                if (expireDate.isAfter(LocalDate.now())) {
//                    cForm.setStatus(FormStatus.Expired);
//                }
//            }
//            return cForm;
//
//        }).collect(Collectors.toList());
//
//    }
}
