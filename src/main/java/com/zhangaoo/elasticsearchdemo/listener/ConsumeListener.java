package com.zhangaoo.elasticsearchdemo.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangaoo.elasticsearchdemo.entity.Material;
import com.zhangaoo.elasticsearchdemo.entity.MaterialHistory;
import com.zhangaoo.elasticsearchdemo.entity.Payload;
import com.zhangaoo.elasticsearchdemo.service.EsMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/04/08 09:52:00<br/>
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "${es.rocketmq.topic}", consumerGroup = "es-material-consumer", messageModel = MessageModel.CLUSTERING)
public class ConsumeListener implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired    
    private EsMaterialService esMaterialService;

    @Override
    public void onMessage(MessageExt message) {
        Payload payload = null;
        try {
            payload = objectMapper.readValue(message.getBody(), Payload.class);
            if(payload != null &&  payload.getIdType() != null && "test".equals(payload.getIdType())){
                List<MaterialHistory> materials = JSON.parseObject(payload.getPayload(),  new TypeReference<List<MaterialHistory>>(){});
                esMaterialService.saveAllMaterialHistory(materials);
            } else {
                //略过
            }
        } catch (Throwable e) {
            log.error("test:", e);
        }
        //重新消费次数超过指定阈值，记录到数据库，本次算消费成功
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {

    }
}
