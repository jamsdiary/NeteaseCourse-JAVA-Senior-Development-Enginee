package com.study.coupon.web;

import com.study.coupon.bean.TbCoupon;
import com.study.coupon.bean.TbCouponDetail;
import com.study.coupon.bean.TbCouponDetailVO;
import com.study.coupon.service.CouponDetailService;
import com.study.coupon.service.CouponService;
import com.study.security.common.context.BaseContextHandler;
import com.study.security.common.msg.BaseResponse;
import com.study.security.common.msg.ObjectRestResponse;
import com.study.security.common.msg.TableResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(description = "个人优惠券使用接口")
@RestController
@RequestMapping("/coupon/detail/op/")
public class CouponDetailController {
    @Autowired
    CouponDetailService couponDetailService;

    @Autowired
    CouponService couponService;

    //TODO 自己的订单，自己的优惠券。
    @ApiOperation(value = "锁定优惠", notes = "同一个订单只能锁定一张优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "couponDetailId", value = "个人优惠券领取的ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "orderId", value = "订单ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "orderUserId", value = "下订单的用户ID", required = true, dataType = "string")
    })
    @RequestMapping(value = "/lockCoupon", method = RequestMethod.POST)
    public ObjectRestResponse lockCoupon(String couponDetailId, String orderId, String orderUserId) {
        ObjectRestResponse response = new ObjectRestResponse();
        // 不是同一个用户，提示非法请求
        String loginUserId = BaseContextHandler.getUserID();
        // TODO 测试代码
        // loginUserId = "99999";
        if (!loginUserId.equals(orderUserId)) {
            // TODO 记录日志
            response.setStatus(403);
            response.setMessage("非法操作");
            return response;
        }
        TbCouponDetail tbCouponDetail = couponDetailService.lockCouponDetail(couponDetailId, orderId, loginUserId);
        if (tbCouponDetail == null) {
            // TODO 记录日志
            response.setStatus(400);
            response.setMessage("操作失败");
            return response;
        }
        // 返回所有内容
        TbCoupon tbCoupon = couponService.queryCouponById(tbCouponDetail.getCouponId());
        response.setData(tbCoupon);
        response.setStatus(200);
        return response;

    }

    @ApiOperation(value = "释放优惠锁定", notes = "取消支付后解锁优惠")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "couponDetailId", value = "个人优惠券领取的ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "orderId", value = "订单ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "orderUserId", value = "下订单的用户ID", required = true, dataType = "string")
    })
    @RequestMapping(value = "/releaseCoupon", method = RequestMethod.POST)
    public ObjectRestResponse releaseCoupon(String couponDetailId, String orderId, String orderUserId) {
        ObjectRestResponse response = new ObjectRestResponse();
        // 不是同一个用户，提示非法请求
        String loginUserId = BaseContextHandler.getUserID();
        // TODO 测试代码
        // loginUserId = "99999";
        if (!loginUserId.equals(orderUserId)) {
            // TODO 记录日志
            response.setStatus(403);
            response.setMessage("非法操作");
            return response;
        }
        TbCouponDetail tbCouponDetail = couponDetailService.releaseCouponDetail(couponDetailId, orderId, loginUserId);
        if (tbCouponDetail == null) {
            // TODO 记录日志
            response.setStatus(400);
            response.setMessage("操作失败");
            return response;
        }
        // 返回所有内容
        TbCoupon tbCoupon = couponService.queryCouponById(tbCouponDetail.getCouponId());
        response.setData(tbCoupon);
        response.setStatus(200);
        return response;
    }

    @ApiOperation(value = "获取用户个人可使用的优惠券", notes = "")
    @RequestMapping(value = "/getCouponDetail", method = RequestMethod.GET)
    public TableResultResponse getCouponDetailByUserId() {
        String loginUserId = BaseContextHandler.getUserID();
        // TODO 测试代码
        // loginUserId = "99999";
        List<TbCouponDetail> tbCouponDetails = couponDetailService.getUnUsedCouponDetailByUserId(loginUserId);
        List<TbCouponDetailVO> tbCouponDetailVOS = new ArrayList<>();
        for (TbCouponDetail tbCouponDetail : tbCouponDetails) {
            TbCouponDetailVO tbCouponDetailVO = new TbCouponDetailVO();
            tbCouponDetailVO.setTbCoupon(couponService.queryCouponById(tbCouponDetail.getCouponId()));
            tbCouponDetailVO.setCouponDetailId(tbCouponDetail.getCouponDetailId());
            tbCouponDetailVOS.add(tbCouponDetailVO);
        }

        TableResultResponse<TbCouponDetail> tableResultResponse = new TableResultResponse(tbCouponDetailVOS.size(), tbCouponDetailVOS);
        tableResultResponse.setStatus(200);
        return tableResultResponse;
    }
}
