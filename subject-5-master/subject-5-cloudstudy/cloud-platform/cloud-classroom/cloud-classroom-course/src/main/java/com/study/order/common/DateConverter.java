package com.study.order.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by andy on 2018/1/13.
 */
public class DateConverter implements Converter<String,Date> {
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String hourFormat = "yyyy-MM-dd HH:mm";
    private static final String shortDateFormat = "yyyy-MM-dd";
    private static final String longDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @Override
    public Date convert(String value) {

        if(StringUtils.isEmpty(value)||"undefined".equalsIgnoreCase(value)) {
            return null;
        }

        value = value.trim();

        try {
            if(value.contains("-")) {
                SimpleDateFormat formatter;
                if(value.contains("T")) {
                    formatter = new SimpleDateFormat(longDateFormat);
                }
                else if(value.contains(":")) {
                    if(value.length() == 16){
                        formatter = new SimpleDateFormat(hourFormat);
                    }else{
                        formatter = new SimpleDateFormat(dateFormat);
                    }

                }else {
                    formatter = new SimpleDateFormat(shortDateFormat);
                }

                Date dtDate = formatter.parse(value);
                return dtDate;
            }else if(value.matches("^\\d+$")) {
                Long lDate = new Long(value);
                return new Date(lDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("parser %s to Date fail", value));
        }
        throw new RuntimeException(String.format("parser %s to Date fail", value));
    }

}