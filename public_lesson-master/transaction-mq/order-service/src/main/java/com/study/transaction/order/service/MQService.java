package com.study.transaction.order.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

/**
 * 这是一个发送MQ消息，修改消息表的地方
 * 
 * @author Tony
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MQService {
	private final Logger logger = LoggerFactory.getLogger(MQService.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void setup() {
		// 消息发送完毕后，则回调此方法 ack代表发送是否成功
		rabbitTemplate.setConfirmCallback(new ConfirmCallback() {
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				// ack为true，代表MQ已经准确收到消息
				if (!ack) {
					return;
				}

				try {
					// 2. 修改本地消息表的状态为“已发送”。删除、修改状态
					String sql = "update tb_distributed_message set msg_status=1 where unique_id=?";
					int count = jdbcTemplate.update(sql, correlationData.getId());

					if (count != 1) {
						logger.warn("警告：本地消息表的状态修改不成功");
					}
				} catch (Exception e) {
					logger.warn("警告：修改本地消息表的状态时出现异常", e);
				}

			}
		});
	}

	/**
	 * 发送MQ消息，修改本地消息表的状态
	 * 
	 * @throws Exception
	 */
	public void sendMsg(JSONObject orderInfo) throws Exception {
		// 1. 发送消息到MQ
		// CorrelationData 当收到消息回执时，会附带上这个参数
		rabbitTemplate.convertAndSend("createOrderExchange", "", orderInfo.toJSONString(),
				new CorrelationData(orderInfo.getString("orderId")));
	}
}
