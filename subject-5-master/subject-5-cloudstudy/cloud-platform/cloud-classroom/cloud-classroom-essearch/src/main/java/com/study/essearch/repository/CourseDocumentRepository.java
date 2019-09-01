package com.study.essearch.repository;

import com.study.essearch.document.Course;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName CourseDocumentRepository
 * @Description 创建一个Repository的相关接口（可以加注解@RepositoryDefinition），这个接口必须继承Repository接口，
 *  * 这里的ElasticsearchRepository的上级上级。。。接口就是继承自Repository
 *  * ElasticsearchRepository接口泛型通常写成<存储的实体类型, 主键类型>，这样就将这个仓库定制化为某个文档的专用，比如这里
 *  * 就是Book文档的专用，我们也可以定义更加通用的Repository，比如
 *  *
 *  * @NoRepositoryBean
 *  * interface MyBaseRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
 *  *   …
 *  * }
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/7/25 23:02
 * @Version 1.0
 */
@Repository
public interface CourseDocumentRepository extends ElasticsearchRepository<Course, String> {
}
