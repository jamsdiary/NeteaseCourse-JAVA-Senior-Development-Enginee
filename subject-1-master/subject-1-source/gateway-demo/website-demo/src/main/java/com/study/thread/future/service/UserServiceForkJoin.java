package com.study.thread.future.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 并行调用http接口
 */
@Service
public class UserServiceForkJoin {
    // 本质是一个线程池,默认的线程数量:CPU的核数
    ForkJoinPool forkJoinPool = new ForkJoinPool(10, ForkJoinPool.defaultForkJoinWorkerThreadFactory,
            null, true);
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询多个系统的数据，合并返回
     */
    public Object getUserInfo(String userId) throws ExecutionException, InterruptedException {
        // 其他例子, 查数据库的多个表数据,分多次查询
        // fork/join
        // forkJoinPool.submit()
        ArrayList<String> urls = new ArrayList<>();
        urls.add("http://www.tony.com/userinfo-api/get?userId=" + userId);
        urls.add("http://www.tony.com/integral-api/get?userId=" + userId);

        HttpJsonRequest httpJsonRequest = new HttpJsonRequest(restTemplate, urls, 0, urls.size() - 1);
        ForkJoinTask<JSONObject> forkJoinTask = forkJoinPool.submit(httpJsonRequest);

        JSONObject result = forkJoinTask.get();
        return result;
    }
}

// 任务
class HttpJsonRequest extends RecursiveTask<JSONObject> {

    RestTemplate restTemplate;
    ArrayList<String> urls;
    int start;
    int end;

    HttpJsonRequest(RestTemplate restTemplate, ArrayList<String> urls, int start, int end) {
        this.restTemplate = restTemplate;
        this.urls = urls;
        this.start = start;
        this.end = end;
    }

    // 就是实际去执行的一个方法入口(任务拆分)
    @Override
    protected JSONObject compute() {
        int count = end - start; // 代表当前这个task需要处理多少数据
        // 自行根据业务场景去判断是否是大任务,是否需要拆分
        if (count == 0) {
            String url = urls.get(start);
            // TODO 如果只有一个接口调用,立刻调用
            long userinfoTime = System.currentTimeMillis();
            String response = restTemplate.getForObject(url, String.class);
            JSONObject value = JSONObject.parseObject(response);
            System.out.println(Thread.currentThread() + " 接口调用完毕" + (System.currentTimeMillis() - userinfoTime) + " #" + url);
            return value;
        } else { // 如果是多个接口调用,拆分成子任务  7,8,   9,10
            System.out.println(Thread.currentThread() + "任务拆分一次");
            int x = (start + end) / 2;
            HttpJsonRequest httpJsonRequest = new HttpJsonRequest(restTemplate, urls, start, x);// 负责处理哪一部分?
            httpJsonRequest.fork();

            HttpJsonRequest httpJsonRequest1 = new HttpJsonRequest(restTemplate, urls, x + 1, end);// 负责处理哪一部分?
            httpJsonRequest1.fork();

            // join获取处理结果
            JSONObject result = new JSONObject();
            result.putAll(httpJsonRequest.join());
            result.putAll(httpJsonRequest1.join());
            return result;
        }
    }
}
