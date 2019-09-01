package com.study.hystrix.service;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 远程调用Movie接口
 */
@Service
public class QueryServiceRemoteCall {

    /**
     * 调用远程的电影信息查询接口
     *
     * @param code 电影编码
     * @return 返回电影信息，map格式
     */
    public HashMap<String, Object> queryMovieInfoByCode(String code) {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("movieId", new Random().nextInt(999999999));
        hashMap.put("code", code);
        hashMap.put("star", "tony");
        hashMap.put("isHandsome", "true");
        return hashMap;
    }

    /**
     * 批量查询 - 调用远程的电影信息查询接口
     *
     * @param codes 多个电影编码
     * @return 返回多个电影信息
     */
    public List<Map<String, Object>> queryMovieInfoByCodeBatch(List<String> codes) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (String code : codes) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("movieId", new Random().nextInt(999999999));
            hashMap.put("code", code);
            hashMap.put("star", "tony");
            hashMap.put("isHandsome", "true");
            result.add(hashMap);
        }
        return result;
    }
}
