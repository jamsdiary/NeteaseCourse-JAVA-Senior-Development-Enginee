package com.study.security.common.msg.auth;

import com.study.security.common.constant.RestCodeConstants;
import com.study.security.common.msg.BaseResponse;

/**
 * token被禁异常返回
 **/
public class TokenForbiddenResponse  extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(RestCodeConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}
