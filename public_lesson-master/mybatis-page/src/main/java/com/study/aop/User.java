package com.study.aop;

/**
 * 用户对象
 * 
 * @author allen
 *
 */
public class User {
	private String cardCode;	// 卡号
	private String name;		// 姓名
	private String homeAddress;	// 住址
	private String sex;			// 性别

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
