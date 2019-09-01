package com.study.essearch.document;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.io.Serializable;
import java.util.Date;

/**
 * dataES自身提供了@Document注解文档，当然它也接受第三方的注解，比如JPA中的@Entity，
 * 但是别混用，比如在Book类上同时用@Entity和@Document注解，一个即可被识别
 * @author allen
 * @version 0.1
 * @date 2018/12/13 15:22
 */
@Document(indexName = "course", type = "doc")
// 注意：由于我们部署的es是使用docker部署的没有添加ik分词器，所以启动会有一个error错误。可以在容器中添加分词器解决该问题
@Mapping(mappingPath = "courseIndex.json") // 解决IK分词不能使用问题
@Data
@ToString
public class Course {

    @Id
    private String id;

    private String name;

    private String image;

    private Double price;

    private String synopsis;

    private String description;

    private String classification;

    private String teacherId;

    private Integer status;

    private Date startTime;

    private Date updateTime;

}
