package com.study.mycat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.study.mycat.model.Country;

public interface CountryMapper {

    @Select("select * from country limit 1")
    Country findOne();
    
    List<Country> selectByExample(Country country);
    
	public Country selectByPrimaryKey(Integer id);

	public void deleteByPrimaryKey(Integer id);

	public void updateByPrimaryKey(Country country);

	public void insert(Country country);
}