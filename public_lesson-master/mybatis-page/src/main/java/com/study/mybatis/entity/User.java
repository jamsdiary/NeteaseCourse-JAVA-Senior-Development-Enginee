package com.study.mybatis.entity;

/**
 * 用户对象实体类
 * 
 * @author allen
 */
public class User {

	/**
	 * id,主键(自增)
	 */
	private int id;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 用户年龄
	 */
	private String userAge;
	/**
	 * 用户地址
	 */
	private String userAddress;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

}