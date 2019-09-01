package com.study.order.service;

import com.alibaba.fastjson.JSONObject;
import com.study.order.bean.Course;
import com.study.order.bean.Order;
import com.study.order.bean.UserStudy;
import com.study.order.client.CouponClient;
import com.study.order.client.CouponDetailClinet;
import com.study.order.client.CourseClient;
import com.study.order.common.OrderStatusEnum;
import com.study.order.mapper.OrderMapper;
import com.study.security.common.msg.TableResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by andy on 2019/1/24.
 */
@Service
@Slf4j
public class OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    CourseClient courseService;

    // 优惠券client
    @Autowired
    CouponClient couponClient;
    @Autowired
    CouponDetailClinet couponDetailClinet;

    @Autowired
    private MsgSender sender;

    @Hmily(confirmMethod = "confirmAddOrder", cancelMethod = "cancelAddOrder")
    public int addOrder(Order order) {
        final String uuid = UUID.randomUUID().toString();
        order.setId(uuid);

        // 查询个人优惠券
        TableResultResponse result = couponDetailClinet.getCouponDetailByUserId();
        // 选择使用那张优惠券，就是得到优惠券id

        // 使用优惠券
        couponClient.lockCoupon("752f30a5-3b6d-4c22-9c5f-4062d2aa99ec",  uuid, "1");

        int result = orderMapper.insert(order);
        return result;
    }

    public void confirmAddOrder(Order order) {
        // 课后作业
        log.info("=========进行订单confirm操作完成================");
    }

    public void cancelAddOrder(Order order) {
        // 课后作业
        log.info("=========进行订单cancel操作完成================");
    }


    public List<Order> getAllOrder() {
        return orderMapper.getAllOrder();
    }

    public List<Order> getOrdersByPage(Integer page, Integer size, String name) {
        int start = (page - 1) * size;
        return orderMapper.getOrdersByPage(start, size, name);
    }


    public Long getCountByKey(String name) {
        return orderMapper.getCountByKey(name);
    }

    public Order getOrder(String id) {
        return orderMapper.selectByPrimaryKey(id);
    }


    public int updateOrder(Order Order) {
        return orderMapper.updateByPrimaryKeySelective(Order);
    }

    public int deleteOrder(String id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    public int orderCourse(int userId, String courseId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderTime(new Date(System.currentTimeMillis()));
        Course course = courseService.getCourse(courseId);
        order.setState(OrderStatusEnum.NOT_PAY.getCode());
        order.setTotal(course.getPrice());
        order.setCourseId(courseId);
        return this.addOrder(order);
    }

    public List<Order> getOrderByUserId(int userId) {
        List<Order> orders = orderMapper.getOrderByUserId(userId);
        orders.stream().forEach(item -> {
            Course course = courseService.getCourse(item.getCourseId());
            if (course != null) {
                item.setCourseName(course.getName());
            }
        });
        return orders;
    }


    public int payOrder(String orderId) {
        Order orderInDb = this.getOrder(orderId);
        if (orderInDb.getState() == 0) {
            Order newOrder = new Order();
            newOrder.setId(orderId);
            newOrder.setState(OrderStatusEnum.PAY_SUCCESS.getCode());
            this.updateOrder(newOrder);
            UserStudy userStudy = new UserStudy();
            userStudy.setCourseId(orderInDb.getCourseId());
            userStudy.setUserId(orderInDb.getUserId());
            userStudy.setStartTime(new Date(System.currentTimeMillis()));
            courseService.addUserStudy(userStudy);
            return 1;
        }
        return 2;
    }


    public int payOrderUsingMq(String orderId) {
        Order newOrder = new Order();
        newOrder.setId(orderId);
        newOrder.setState(OrderStatusEnum.PAY_SUCCESS.getCode());
        this.updateOrder(newOrder);
        Order orderInDb = this.getOrder(orderId);
        UserStudy userStudy = new UserStudy();
        userStudy.setCourseId(orderInDb.getCourseId());
        userStudy.setUserId(orderInDb.getUserId());
        userStudy.setStartTime(new Date(System.currentTimeMillis()));
        String userStudyJsonStr = JSONObject.toJSONString(userStudy);
        sender.topicSend(userStudyJsonStr);
//        courseService.addUserStudy(userStudy);
        return 1;
    }

}
