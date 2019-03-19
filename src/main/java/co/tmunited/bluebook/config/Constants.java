package co.tmunited.bluebook.config;

import co.tmunited.bluebook.domain.enumeration.FaxState;

import java.util.Arrays;
import java.util.List;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";
    // Spring profile for development and production, see http://jhipster.github.io/profiles/
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    // Spring profile used when deploying to Heroku
    public static final String SPRING_PROFILE_HEROKU = "heroku";
    // Spring profile used to disable swagger
    public static final String SPRING_PROFILE_SWAGGER = "swagger";
    // Spring profile used to disable running liquibase
    public static final String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";

    public static final String SYSTEM_ACCOUNT = "system";

    public static final String BAR_CODE = getBarCode();

    public static final String BUILD_VERSION = System.getenv("BUILD_VERSION");

    public static final int PICTURE_WITH = getPictureWith();

    public static final String FAX_FROM_NUMBER= "+17542081690";

    public static final String FAX_STATUS_QUEUED ="queued";

    public static final String FAX_INTERVAL_UPDATE = System.getenv("FAX_INTERVAL_UPDATE")!=null?System.getenv("FAX_INTERVAL_UPDATE"): "300000";

    private static int getPictureWith() {
        String with = System.getenv("PICTURE_WITH");

        if(with != null)
            return  Integer.parseInt(with);
        else
            return 500;

    }

    public static List<String> FAX_FINAL_STATUS =  Arrays.asList( new String[]{ FaxState.RECEIVED.name(), FaxState.NO_ANSWER.name(), FaxState.DELIVERED.name(), FaxState.FAILED.name(), FaxState.CANCELED.name()});

    private Constants() {
    }


    private static String getBarCode() {
        String bc = System.getenv("APPLICATION_RESULTTYPE_PATIENT");
        if(bc == null)
            bc = System.getProperty("APPLICATION_RESULTTYPE_PATIENT");

        if(bc == null)
            bc= "B";

        return bc;
    }



}
