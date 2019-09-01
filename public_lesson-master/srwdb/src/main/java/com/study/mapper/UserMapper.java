package com.study.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.study.entity.Person;
import com.study.entity.User;

/**
 * 数据访问层接口
 *
 * @author allen
 */
public interface UserMapper {

    // 借助数组进行分页
    public List<User> selectUsersByArray();

    // 借助Sql语句进行分页
    public List<User> selectUsersBySql(Map<String, Object> data);

    // RowBounds实现分页
    public List<User> selectUsersByRowBounds(RowBounds rowBounds);

    // 插件式分页
    public List<User> selectUsersByPage(Map<String, Object> map);

    // 插入
    public int user_insert(User user);

    // 删除
    public int user_delete(User user);



    public List<Person> getPerson();

    public int insertPerson(Person p);
}
