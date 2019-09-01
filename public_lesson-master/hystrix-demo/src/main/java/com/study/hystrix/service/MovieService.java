package com.study.hystrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;

/**
 * 电影信息服务类
 */
@Service
public class MovieService {

    class Request {
        String movieCode;
        CompletableFuture<Map<String, Object>> completableFuture;
    }
    // 集合，积攒请求，每N毫秒处理

    LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduledExecutorPool = Executors.newScheduledThreadPool(5);
        scheduledExecutorPool.scheduleAtFixedRate(() -> {
            // TODO 取出所有queue的请求，生成一次批量查询
            int size = queue.size();
            System.out.println("此次处理多少请求：" + size);
            if (size == 0) {
                return;
            }
            // 1、 取出
            ArrayList<Request> requests = new ArrayList<>();
            ArrayList<String> movieCodes = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Request request = queue.poll();
                requests.add(request);
                movieCodes.add(request.movieCode);
            }
            // 2、 组装一个批量查询 （不会比单次查询慢很多）
            List<Map<String, Object>> mapList = queryServiceRemoteCall.queryMovieInfoByCodeBatch(movieCodes);

            // 3、 分发响应结果，给每一个request用户请求 （多线程 之间的通信）
            // {"code":"500","star":"tony","isHandsome":"true","movieId":465786824}
            // {"code":"600","star":"tony","isHandsome":"true","movieId":465786824}
            // {"code":"700","star":"tony","isHandsome":"true","movieId":465786824}
            HashMap<String, Map<String, Object>> resultMap = new HashMap<>(); //  1000---- 007
            for (Map<String, Object> map : mapList) {
                String code = map.get("code").toString();
                resultMap.put(code, map);
            }

            for (Request req : requests) { // 1000
                Map<String, Object> result = resultMap.get(req.movieCode);
                // 怎么通知对应的1000多个线程，取结果呢？
                req.completableFuture.complete(result);
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }


    @Autowired
    QueryServiceRemoteCall queryServiceRemoteCall;

    // 1000 用户请求，1000个线程
    public Map<String, Object> queryMovie(String movieCode) throws ExecutionException, InterruptedException {
        Request request = new Request();
        request.movieCode = movieCode;
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        request.completableFuture = future;
        queue.add(request);
        // TODO 等待批量查询的结果  异步编程
        return future.get(); // 等待其他线程通知拿结果
        //queryServiceRemoteCall.queryMovieInfoByCode(movieCode);
        // 调用远程的电影信息查询接口  （发挥不出最大性能）
        // return queryServiceRemoteCall.queryMovieInfoByCode(movieCode);
    }


}
