package com.zhangaoo.elasticsearchdemo.service;

import org.apache.rocketmq.client.producer.SendResult;

import java.util.List;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/04/11 10:16:00<br/>
 */
public interface MqService {
    /**
     * 异步发送消息
     *
     * @param topic
     * @param msg
     */
    void asyncSend(String topic, Object msg);

    /**
     * 同步消息发送，一次只发送一条消息
     *
     * @param topic
     * @param msg
     * @param timeout
     * @return
     */
    SendResult syncSend(String topic, Object msg, long timeout);

    /**
     * 同步消息批量发送
     *
     * @param topic
     * @param msg
     * @param timeout
     */
    void batchSyncSend(String topic, List<Object> msg, long timeout);
}
