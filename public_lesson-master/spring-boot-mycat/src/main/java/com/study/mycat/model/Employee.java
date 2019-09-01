package com.study.mycat.model;

public class Employee extends BaseEntity {
	private String name;

	private Integer shardingId;
	
	private String isNew;

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShardingId() {
		return shardingId;
	}

	public void setShardingId(Integer shardingId) {
		this.shardingId = shardingId;
	}

}
