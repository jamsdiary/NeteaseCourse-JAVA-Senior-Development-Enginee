package com.study.mvc.service.impl;

import java.util.Map;

import com.study.mvc.annotation.Service;
import com.study.mvc.service.StudyService;

@Service("studyService")
public class StudyServiceImpl implements StudyService {
	// 注入数据库dao操作
	
	@Override
	public int insert(Map map) {
		System.out.println("调用了 StudyServiceImpl 类中的方法:" + "insert");
		return 0;
	}

	@Override
	public int delete(Map map) {
		System.out.println("调用了 StudyServiceImpl 类中的方法:" + "delete");
		return 0;
	}

	@Override
	public int update(Map map) {
		System.out.println("调用了 StudyServiceImpl 类中的方法:" + "update");
		return 0;
	}

	@Override
	public int select(Map map) {
		System.out.println("调用了 StudyServiceImpl 类中的方法:" + "select");
		return 0;
	}
}
