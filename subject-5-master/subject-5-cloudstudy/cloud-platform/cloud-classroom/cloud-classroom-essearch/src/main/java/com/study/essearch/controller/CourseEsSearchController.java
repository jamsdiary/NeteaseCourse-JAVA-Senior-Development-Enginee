package com.study.essearch.controller;

import com.study.essearch.document.Course;
import com.study.essearch.page.Page;
import com.study.essearch.service.CourseEsSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * elasticsearch 搜索
 *
 * @author andy
 * @version 0.1
 * @date 2018/12/13 15:09
 */
@RestController
@RequestMapping("/cloud/classroom/es/course")
public class CourseEsSearchController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CourseEsSearchService esSearchService;

    /**
     * 新增 / 修改索引
     *
     * @return
     */
    @PostMapping("save")
    public String add(@RequestBody Course course) {
        esSearchService.save(course);
        return "success";
    }

    /**
     * 根据ID获取
     *
     * @return
     */
    @GetMapping("get/{id}")
    public Course getById(@PathVariable String id) {
        return esSearchService.getById(id);
    }

    /**
     * 根据获取全部
     *
     * @return
     */
    @GetMapping("get_all")
    public List<Course> getAll() {
        return esSearchService.getAll();
    }

    /**
     * 删除索引
     *
     * @return
     */
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable String id) {
        esSearchService.delete(id);
        return "success";
    }

    /**
     * 清空索引
     *
     * @return
     */
    @DeleteMapping("delete_all")
    public String deleteAll(@PathVariable String id) {
        esSearchService.deleteAll();
        return "success";
    }

    /**
     * 搜索
     *
     * @param keyword
     * @return
     */
    @PostMapping("query/{keyword}")
    public List<Course> query(@PathVariable String keyword) {
        return esSearchService.query(keyword, Course.class);
    }

    /**
     * 搜索，命中关键字高亮
     * http://localhost:8080/query_hit?keyword=无印良品荣耀&indexName=orders&fields=courseName,courseDesc
     *
     * @param keyword   关键字
     * @param indexName 索引库名称
     * @param fields    搜索字段名称，多个以“，”分割
     * @return
     */
    @GetMapping("query_hit")
    //public List<Map<String, Object>> queryHit(@RequestParam String keyword, @RequestParam String indexName, @RequestParam String fields) {
    public List<Map<String, Object>> queryHit(@RequestParam String keyword) {
        String indexName = "course";
        //String fields = "name,synopsis,description,classification";
        String fields = "name";

        String[] fieldNames = {};
        if (fields.contains(",")) {
            fieldNames = fields.split(",");
        } else {
            fieldNames[0] = fields;
        }

        return esSearchService.queryHit(keyword, indexName, fieldNames);
    }

    /**
     * 搜索，命中关键字高亮
     * http://localhost:8080/query_hit_page?keyword=无印良品荣耀&indexName=orders&fields=courseName,courseDesc&pageNo=1&pageSize=1
     *
     * @param pageNo    当前页
     * @param pageSize  每页显示的数据条数
     * @param keyword   关键字
     * @param indexName 索引库名称
     * @param fields    搜索字段名称，多个以“，”分割
     * @return
     */
    @GetMapping("query_hit_page")
    public Page<Map<String, Object>> queryHitByPage(@RequestParam int pageNo, @RequestParam int pageSize
            , @RequestParam String keyword, @RequestParam String indexName, @RequestParam String fields) {
        String[] fieldNames = {};
        if (fields.contains(",")) {
            fieldNames = fields.split(",");
        } else {
            fieldNames[0] = fields;
        }
        return esSearchService.queryHitByPage(pageNo, pageSize, keyword, indexName, fieldNames);
    }

    /**
     * 删除索引库
     *
     * @param indexName
     * @return
     */
    @GetMapping("delete_index/{indexName}")
    public String deleteIndex(@PathVariable String indexName) {
        esSearchService.deleteIndex(indexName);
        return "success";
    }
}
