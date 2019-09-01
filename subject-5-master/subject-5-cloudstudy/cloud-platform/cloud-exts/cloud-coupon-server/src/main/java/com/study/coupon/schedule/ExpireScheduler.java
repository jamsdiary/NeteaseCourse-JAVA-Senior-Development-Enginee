package com.study.coupon.schedule;//package com.study.coupon.schedule;
//
//import com.study.coupon.bean.TbCoupon;
//import com.study.coupon.service.CouponService;
//import com.xxl.job.core.biz.model.ReturnT;
//import com.xxl.job.core.handler.IJobHandler;
//import com.xxl.job.core.handler.annotation.JobHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * 每天执行优惠券过期处理
// */
//@JobHandler(value = "expireScheduler")
//@Component
//public class ExpireScheduler extends IJobHandler {
//    private Logger logger = LoggerFactory.getLogger(ExpireScheduler.class);
//
//    @Override
//    public ReturnT<String> execute(String arg0) throws Exception {
//        // TODO 此处每天扫库即可~~ 但是每个公司业务会有不同，所以不做具体实现
//        // TODO 除了修改有效期之外，还需要把Redis里面的一些记录也清除掉
//        logger.info("执行优惠券过期处理....ing....");
//
//        return new ReturnT<String>("ok");
//    }
//
//}
