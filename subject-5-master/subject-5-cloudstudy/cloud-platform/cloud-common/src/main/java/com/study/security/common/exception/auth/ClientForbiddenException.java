package com.study.security.common.exception.auth;

import com.study.security.common.constant.CommonConstants;
import com.study.security.common.exception.BaseException;

public class ClientForbiddenException extends BaseException {
    public ClientForbiddenException(String message) {
        super(message, CommonConstants.EX_CLIENT_FORBIDDEN_CODE);
    }

}
