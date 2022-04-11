package com.zhangaoo.elasticsearchdemo.service.impl;

import com.zhangaoo.elasticsearchdemo.service.MqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/04/11 10:17:00<br/>
 */
@Slf4j
@Service
public class MqServiceImpl implements MqService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    @Override
    public void asyncSend(String topic, Object event) {
        rocketMQTemplate.asyncSend(topic, event, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("Async send success:" + sendResult.toString());
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("Sending exception:", throwable);
            }
        });
    }

    @Override
    public SendResult syncSend(String topic, Object msg, long timeout) {
        SendResult res = rocketMQTemplate.syncSend(topic, msg, timeout);
        if (res.getSendStatus().equals(SendStatus.SEND_OK)) {
            log.info("Sync send success:" + res.toString());
        } else {
            log.error("Sync send failed:" + res.toString());
        }
        return res;
    }

    @Override
    public void batchSyncSend(String topic, List<Object> msg, long timeout) {
        List<Message> msgs = new ArrayList<>(msg.size());
        for (int i = 0; i < msg.size(); i++) {
            msgs.add(MessageBuilder.withPayload(msg.get(i)).setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + i).build());
        }
        SendResult res = rocketMQTemplate.syncSend(topic, msgs, timeout);
        if (res.getSendStatus().equals(SendStatus.SEND_OK)) {
            log.info("Batch sync send success:" + res.toString());
        } else {
            log.error("Batch sync send failed:" + res.toString());
        }
    }
}
