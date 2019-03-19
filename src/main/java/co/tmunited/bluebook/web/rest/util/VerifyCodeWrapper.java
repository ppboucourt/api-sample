package co.tmunited.bluebook.web.rest.util;

/**
 * Created by adriel on 8/6/17.
 */
public class VerifyCodeWrapper {
   private  String code;
    private String browserUuid;
   private  Boolean rememberMe;

    public VerifyCodeWrapper(){}

    public VerifyCodeWrapper(String code, String browserUuid, Boolean rememberMe) {
        this.code = code;
        this.browserUuid = browserUuid;
        this.rememberMe = rememberMe;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrowserUuid() {
        return browserUuid;
    }

    public void setBrowserUuid(String browserUuid) {
        this.browserUuid = browserUuid;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
