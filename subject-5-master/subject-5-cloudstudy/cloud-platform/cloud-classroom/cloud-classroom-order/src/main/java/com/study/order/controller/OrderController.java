package com.study.order.controller;

import com.study.order.bean.Order;
import com.study.order.bean.RespBean;
import com.study.order.common.JwtUtil;
import com.study.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cloud/classroom/order")
public class OrderController<add> {

    @Autowired
    private OrderService orderService;

    /**
     * 订单支付
     *
     * @param orderId
     * @return
     */
    @GetMapping(value = "/pay/order")
    public RespBean payOrder(String orderId) {
        int userId = Integer.valueOf(JwtUtil.getUserId());
        int result = orderService.payOrder(orderId);
        if (result == 1) {
            return RespBean.ok("购买成功!");
        }
        if (result == 2) {
            return RespBean.error("不能重复购买!");
        }
        return RespBean.error("购买失败!");
    }

    /**
     * 订单支付，mq方式
     *
     * @param orderId
     * @return
     */
    @GetMapping(value = "/pay/order/mq")
    public RespBean payOrderUsingMq(String orderId) {
        int userId = Integer.valueOf(JwtUtil.getUserId());
        if (orderService.payOrderUsingMq(orderId) == 1) {
            return RespBean.ok("购买成功!");
        }
        return RespBean.error("购买失败!");
    }

    /**
     * 生成订单 - 分布式事务 tcc、
     * 1、生成订单，把订单号传给优惠券service
     * 2、选择优惠券，锁定优惠券，需要订单id和优惠卷id
     * 3、下单成功调用支付
     * 4、未支付（取消订单或者超时），释放优惠券
     *
     * @param order
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public RespBean addOrder(@RequestBody Order order) {

        if (orderService.addOrder(order) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    /**
     * 根据id获取订单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order getOrder(@PathVariable String id) {
        return orderService.getOrder(id);
    }

    /**
     * 订单列表，所有订单
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        List<Order> Orders = orderService.getAllOrder();
        return Orders;
    }

    /**
     * 订单列表，分页显示
     *
     * @param page
     * @param size
     * @param key
     * @return
     */
    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    public Map<String, Object> getOrdersByPage(@RequestParam(defaultValue = "1") Integer page,
                                               @RequestParam(defaultValue = "10") Integer size, String key) {
        key = Optional.ofNullable(key).orElse("");
        Long count = orderService.getCountByKey(key);
        List<Order> Orders = null;
        Orders = orderService.getOrdersByPage(page, size, key);
        Map<String, Object> map = new HashMap<>();
        map.put("Orders", Orders);
        map.put("count", count);
        return map;
    }

    /**
     * 更新订单信息
     *
     * @param Order
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public RespBean updateOrder(Order Order) {
        if (orderService.updateOrder(Order) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    /**
     * 根据id删除订单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RespBean deleteOrder(@PathVariable String id) {
        if (orderService.deleteOrder(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    /**
     * 订购课程
     *
     * @param courseId
     * @return
     */
    @GetMapping(value = "/order/course")
    public RespBean orderCourse(String courseId) {
        int userId = Integer.valueOf(JwtUtil.getUserId());
        if (orderService.orderCourse(userId, courseId) == 1) {
            return RespBean.ok("订购成功!");
        }
        return RespBean.error("订购失败!");
    }

    /**
     * 获取当前用户订单
     *
     * @return
     */
    @GetMapping(value = "/mine/order")
    public List<Order> getMyOrders() {
        int userId = Integer.valueOf(JwtUtil.getUserId());
        List<Order> orders = orderService.getOrderByUserId(userId);
        return orders;
    }

}
