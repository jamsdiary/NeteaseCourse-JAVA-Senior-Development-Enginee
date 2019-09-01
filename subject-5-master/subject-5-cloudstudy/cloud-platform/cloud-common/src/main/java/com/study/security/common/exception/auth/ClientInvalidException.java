package com.study.security.common.exception.auth;

import com.study.security.common.constant.CommonConstants;
import com.study.security.common.exception.BaseException;

public class ClientInvalidException extends BaseException {
    public ClientInvalidException(String message) {
        super(message, CommonConstants.EX_CLIENT_INVALID_CODE);
    }
}
