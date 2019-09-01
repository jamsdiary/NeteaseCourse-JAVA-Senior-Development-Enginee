package com.study.annotation.model;

import java.io.Serializable;

/**
 * 用户实体类
 * @author allen
 */
public class User implements Serializable {
	private static final long serialVersionUID = -5575893900970589345L;

	// 用户id
	private String id;
	// 用户名
	private String userName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
