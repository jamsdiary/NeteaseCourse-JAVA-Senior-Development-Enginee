package com.study.hystrix.web;

import com.study.hystrix.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 页面查询接口
 */
@RestController
public class MovieApiController {
    @Autowired
    MovieService movieService;

    @RequestMapping("/movie/query")
    public Map<String, Object> queryMovie(String movieCode) throws ExecutionException, InterruptedException {
        return movieService.queryMovie(movieCode);
    }
}
