package com.zhangaoo.elasticsearchdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2020/04/24 15:29:00<br/>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payload implements Serializable {
    /**
     * 消息
     */
    private String payload;
    /**
     * 消息类型
     */
    private String idType;
}
