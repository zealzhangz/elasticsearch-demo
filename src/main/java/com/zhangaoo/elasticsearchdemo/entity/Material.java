package com.zhangaoo.elasticsearchdemo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/04/06 17:09:00<br/>
 */
@Data
@Document(indexName = "material")
public class Material implements Serializable {
    /**
     * 物料编码
     */
    @Id
    private String matnr;
    @Field(type = FieldType.Text, analyzer = "ik_smart",searchAnalyzer = "ik_smart")
    private String maktx;
    /**
     * 单位
     */
    @Field(type = FieldType.Keyword)
    private String meins;

    @Field(type = FieldType.Keyword)
    private String meinsZh;

    @Field(type = FieldType.Keyword)
    private String matkl;

    @Field(type = FieldType.Keyword)
    private String matklZh;
    
    private String werks;

    @Field(type = FieldType.Keyword)
    private Date createTime;

    /**
     * 价格
     */
    @Field(type = FieldType.Double)
    private Double price;
    
    /**
     * 库存数量
     */
    private Integer num;
    
}
