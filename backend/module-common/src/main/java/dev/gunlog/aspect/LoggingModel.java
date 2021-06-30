package dev.gunlog.aspect;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class LoggingModel {
    private String uri;
    private String ip;
    private String requestClassName;
    private String requestMethodName;
    private String requestParams;
    private String responseClassName;
    private String responseMethodName;
    private long processMilliSecond;
    private Object result;

    @Builder
    public LoggingModel(String uri, String ip, String requestClassName, String requestMethodName, String requestParams, String responseClassName, String responseMethodName, long processMilliSecond, Object result) {
        this.uri = uri;
        this.ip = ip;
        this.requestClassName = requestClassName;
        this.requestMethodName = requestMethodName;
        this.requestParams = requestParams;
        this.responseClassName = responseClassName;
        this.responseMethodName = responseMethodName;
        this.processMilliSecond = processMilliSecond;
        this.result = result;
    }

    public void printLog() {
        log.info("=======================");
        log.info("-----------> URI      : "+this.uri);
        log.info("-----------> IP       : "+this.ip);
        log.info(String.format("-----------> REQUEST  : %s(%s) = %s", this.requestClassName, this.requestMethodName, this.requestParams));
        log.info(String.format("-----------> RESPONSE : %s(%s) = %s (%sms)", this.responseClassName, this.responseMethodName, this.result, this.processMilliSecond));
    }
}