package com.study.security.common.msg.auth;

import com.study.security.common.constant.RestCodeConstants;
import com.study.security.common.msg.BaseResponse;

/**
 * token错误返回
 **/
public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
