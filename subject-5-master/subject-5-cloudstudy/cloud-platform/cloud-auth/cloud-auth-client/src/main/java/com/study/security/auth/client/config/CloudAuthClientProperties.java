package com.study.security.auth.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;

/**
 * 权限验证相关的配置项
 */
@ConfigurationProperties(prefix = "auth.client")
public class CloudAuthClientProperties {
    /**
     * 需要用户和服务认证判断的路径
     */
    private ArrayList<String> includePathPatterns = new ArrayList<>();

    private byte[] pubKeyByte;
    private String id;
    private String secret;
    private String tokenHeader;
    private String applicationName;

    public ArrayList<String> getIncludePathPatterns() {
        return includePathPatterns;
    }

    public void setIncludePathPatterns(ArrayList<String> includePathPatterns) {
        this.includePathPatterns = includePathPatterns;
    }

    public byte[] getPubKeyByte() {
        return pubKeyByte;
    }

    public void setPubKeyByte(byte[] pubKeyByte) {
        this.pubKeyByte = pubKeyByte;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
