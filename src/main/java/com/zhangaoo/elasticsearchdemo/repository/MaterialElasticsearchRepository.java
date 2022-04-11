package com.zhangaoo.elasticsearchdemo.repository;

import com.zhangaoo.elasticsearchdemo.entity.Material;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/04/06 17:30:00<br/>
 */
@Repository
public interface MaterialElasticsearchRepository extends ElasticsearchRepository<Material,String> {
    
}
