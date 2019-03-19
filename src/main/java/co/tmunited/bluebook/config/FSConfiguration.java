package co.tmunited.bluebook.config;

import ch.qos.logback.classic.LoggerContext;
import co.tmunited.bluebook.web.rest.client.FileRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Configuration
public class FSConfiguration {

    private final Logger log = LoggerFactory.getLogger(FSConfiguration.class);

    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Inject
    private JHipsterProperties jHipsterProperties;

    @PostConstruct
    private void init() {

        try {
            FileRestClient.getInstance().init(jHipsterProperties.getFileStorage().getRemote().getUserName(),
                jHipsterProperties.getFileStorage().getRemote().getUserAuthorities(),
                jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret(),
                jHipsterProperties.getFileStorage().getRemote().getTokenValidityInSeconds());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
