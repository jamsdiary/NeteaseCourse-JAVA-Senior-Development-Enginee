package com.study.security.common.exception.auth;

import com.study.security.common.constant.CommonConstants;
import com.study.security.common.exception.BaseException;

public class UserTokenException extends BaseException {
    public UserTokenException(String message) {
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}
