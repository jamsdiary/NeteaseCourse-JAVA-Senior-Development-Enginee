package com.study.essearch.document;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @description , dataES自身提供了@Document注解文档，当然它也接受第三方的注解，比如JPA中的@Entity，
 * 但是别混用，比如在Book类上同时用@Entity和@Document注解，一个即可被识别
 * replicas = 0, refreshInterval = "-1"
 */
//@Document(indexName = "book", type = "book", shards = 1, replicas = 0, refreshInterval = "-1")
@Document(indexName = "book", type = "book")
@Data
@ToString
public class Book {

    @Id
    private String id;

    private String name;

    private String author;

}
