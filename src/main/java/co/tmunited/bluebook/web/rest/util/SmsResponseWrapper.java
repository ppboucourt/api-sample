package co.tmunited.bluebook.web.rest.util;

import com.titicservices.dialer.core.domain.SmsResponse;

/**
 * Created by adriel on 4/25/17.
 */
public class SmsResponseWrapper {
    private SmsResponse smsResponse;
    private String code;

    public SmsResponseWrapper() {
    }

    public SmsResponseWrapper(SmsResponse smsResponse, String code) {
        this.smsResponse = smsResponse;
        this.code = code;
    }

    public SmsResponse getSmsResponse() {
        return smsResponse;
    }

    public void setSmsResponse(SmsResponse smsResponse) {
        this.smsResponse = smsResponse;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
