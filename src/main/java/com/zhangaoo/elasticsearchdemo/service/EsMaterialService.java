package com.zhangaoo.elasticsearchdemo.service;

import com.zhangaoo.elasticsearchdemo.entity.Material;
import com.zhangaoo.elasticsearchdemo.repository.MaterialElasticsearchRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/04/08 08:50:00<br/>
 */
@Service
public class EsMaterialService {
    @Autowired
    private MaterialElasticsearchRepository materialElasticsearchRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    public void saveMaterial(Material m) {
        materialElasticsearchRepository.save(m);
    }

    public void saveAllMaterial(List<Material> materials) {
        materialElasticsearchRepository.saveAll(materials);
    }

    public void deleteMaterialById(String id) {
        materialElasticsearchRepository.deleteById(id);
    }

    public List<Object> searchMaterial(String keyword, int current, int limit) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "maktx", "matklZh"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))   // 默认按匹配度排序
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime.keyword").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current, limit))
                .withHighlightFields(
                        new HighlightBuilder.Field("matnr").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("maktx").preTags("<em>").postTags("</em>")
                ).build();

        SearchHits<Material> search = elasticsearchRestTemplate.search(searchQuery, Material.class);

        // 得到查询结果返回的内容
        List<SearchHit<Material>> searchHits = search.getSearchHits();
        // 设置一个需要返回的实体类集合
        List<Material> discussPosts = new ArrayList<>();

        // 遍历返回的内容进行处理
        for (SearchHit<Material> searchHit : searchHits) {
            // 高亮的内容
            Map<String, List<String>> highLightFields = searchHit.getHighlightFields();
            // 将高亮的内容填充到content中
            searchHit.getContent().setMatnr(highLightFields.get("matnr") == null ? searchHit.getContent().getMatnr() : highLightFields.get("matnr").get(0));
            searchHit.getContent().setMaktx(highLightFields.get("maktx") == null ? searchHit.getContent().getMaktx() : highLightFields.get("maktx").get(0));
            // 放到实体类中
            discussPosts.add(searchHit.getContent());
        }
        List<Object> res = new ArrayList<>();
        res.add((int) search.getTotalHits());
        res.add(discussPosts);
        return res;
    }
}
