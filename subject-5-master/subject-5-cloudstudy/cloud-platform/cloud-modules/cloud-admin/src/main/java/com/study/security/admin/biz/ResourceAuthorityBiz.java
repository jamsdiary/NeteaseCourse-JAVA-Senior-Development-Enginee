package com.study.security.admin.biz;

import com.study.security.admin.entity.ResourceAuthority;
import com.study.security.admin.mapper.ResourceAuthorityMapper;
import com.study.security.common.biz.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceAuthorityBiz extends BaseBiz<ResourceAuthorityMapper,ResourceAuthority> {
}
