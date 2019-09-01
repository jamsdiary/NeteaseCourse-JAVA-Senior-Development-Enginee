package com.study.security.admin.biz;

import com.study.security.admin.entity.GroupType;
import org.springframework.stereotype.Service;

import com.study.security.admin.mapper.GroupTypeMapper;
import com.study.security.common.biz.BaseBiz;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GroupTypeBiz extends BaseBiz<GroupTypeMapper,GroupType> {
}
