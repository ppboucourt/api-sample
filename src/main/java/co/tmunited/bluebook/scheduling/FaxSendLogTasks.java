package co.tmunited.bluebook.scheduling;

import co.tmunited.bluebook.config.Constants;
import co.tmunited.bluebook.service.FaxSendLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@EnableAsync
@EnableScheduling
public class FaxSendLogTasks {


    protected static final Logger log = LoggerFactory.getLogger(FaxSendLogTasks.class);

    @Inject
    private FaxSendLogService faxSendLogService;

    @Scheduled(initialDelay=10000, fixedRate= 300000)
    public void updateFaxSendLog() {
        log.info("Executing Update Fax Send Scheduled");
        faxSendLogService.updateFaxState();
    }

    public FaxSendLogService getFaxSendLogService() {
        return faxSendLogService;
    }

    public void setFaxSendLogService(FaxSendLogService faxSendLogService) {
        this.faxSendLogService = faxSendLogService;
    }
}
