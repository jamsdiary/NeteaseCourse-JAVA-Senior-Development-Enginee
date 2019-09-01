package com.study.mike.sharding.jdbc.sharding;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

public class OrderTimePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date>{
	
	Date[] dateRanges = new Date[2];
	
	{
		Calendar cal = Calendar.getInstance();
		cal.set(2019, 1, 1, 0, 0, 0);
		dateRanges[0] = cal.getTime();
		cal.set(2020, 1, 1, 0, 0, 0);
		dateRanges[1] = cal.getTime();
	}

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
		
		Date d = shardingValue.getValue();
		Iterator<String> ite = availableTargetNames.iterator();
		String target = null;
		for(Date s : dateRanges) {
			target = ite.next();
			if(d.before(s)) {
				break;
			}
		}
		return target;
	}

}
