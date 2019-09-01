package com.study.annotation.mapper;

import java.util.List;

import com.study.annotation.model.Order;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@CacheNamespace
public interface OrderMapper {

	List<Order> query(@Param("customerId") String customerId);
}
