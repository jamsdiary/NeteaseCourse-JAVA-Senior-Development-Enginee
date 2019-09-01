package com.study.annotation.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.annotation.mapper.UserMapper;
import com.study.annotation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// 本类内方法指定使用缓存时，默认的名称就是userCache
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User insertUser(User u) {
        this.userMapper.insert(u);
        return this.userMapper.find(u.getId());
    }

    @CachePut(key = "#p0.id")
    public User updateUser(User u) {
        this.userMapper.update(u);
        return this.userMapper.find(u.getId());
    }

    public User findById(String id) {
        System.err.println("根据id=" + id + "获取用户对象，从数据库中获取");
        return this.userMapper.find(id);
    }

    public void deleteById(String id) {
        this.userMapper.delete(id);
    }

    public PageInfo<User> queryPage(String userName, int pageNum,
                                    int pageSize) {
        Page<User> page = PageHelper.startPage(pageNum, pageSize);
        // PageHelper会自动拦截到下面这查询sql
        this.userMapper.query(userName);
        return page.toPageInfo();
    }

}
