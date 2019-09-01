package com.study.security.common.exception.auth;

import com.study.security.common.constant.CommonConstants;
import com.study.security.common.exception.BaseException;

public class UserInvalidException extends BaseException {
    public UserInvalidException(String message) {
        super(message, CommonConstants.EX_USER_PASS_INVALID_CODE);
    }
}
