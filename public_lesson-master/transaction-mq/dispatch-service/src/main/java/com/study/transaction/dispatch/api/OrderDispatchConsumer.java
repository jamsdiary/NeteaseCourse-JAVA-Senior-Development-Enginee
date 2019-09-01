package com.study.transaction.dispatch.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.study.transaction.dispatch.service.DispatchService;

/**
 * 消费者，取调度队列
 * 
 * @author Tony
 *
 */
@Component
public class OrderDispatchConsumer {
	private final Logger logger = LoggerFactory.getLogger(OrderDispatchConsumer.class);

	@Autowired
	DispatchService dispatchService;

	@RabbitListener(queues = "orderDispatchQueue")
	public void messageConsumer(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
			throws Exception {
		try {
			// mq里面的数据转为json对象
			JSONObject orderInfo = JSONObject.parseObject(message);
			logger.warn("收到MQ里面的消息：" + orderInfo.toJSONString());
			Thread.sleep(5000L);

			// 执行业务操作，同一个数据不能处理两次，根据业务情况去重，保证幂等性。 （拓展：redis记录处理情况）
			String orderId = orderInfo.getString("orderId");
			// 这里就是一个分配外卖小哥...
			dispatchService.dispatch(orderId);
			// ack - 告诉MQ，我已经收到啦
			channel.basicAck(tag, false);
		} catch (Exception e) {
			// 异常情况 :根据需要去： 重发/ 丢弃
			// 重发一定次数后， 丢弃， 日志告警
			channel.basicNack(tag, false, false);
			// 系统 关键数据，永远是有人工干预
		}
		// 如果不给回复，就等这个consumer断开链接后，mq-server会继续推送

	}
}