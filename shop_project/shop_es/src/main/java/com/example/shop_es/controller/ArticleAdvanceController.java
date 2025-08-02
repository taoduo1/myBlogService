package com.example.shop_es.controller;

import com.example.shop_common.entity.es.ArticleEntity;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-08-22 21:44
 */
@RestController
@RequestMapping("/elk")
public class ArticleAdvanceController {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    private ElasticsearchOperations operations;

    /**
     * 分页查询
     *
     * @param pageNum  页码,从0开始
     * @param pageSize 分页大小
     * @return 查询结果
     */
    @GetMapping("/queryPage")
    public String queryPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        NativeSearchQuery query = new NativeSearchQuery(new BoolQueryBuilder());
        query.setPageable(PageRequest.of(pageNum, pageSize));
        // 方法1
        SearchHits<ArticleEntity> search1 = restTemplate.search(query, ArticleEntity.class);
        // 方法2
        // SearchHits<ArticleEntity> search2 = operations.search(query, ArticleEntity.class);
        List<ArticleEntity> articles1 = search1.getSearchHits().stream().map(SearchHit::getContent).toList();
        //List<ArticleEntity> articles2 = search2.getSearchHits().stream().map(SearchHit::getContent).toList();
        return articles1.toString();
    }

    /**
     * 滚动查询
     *
     * @param scrollId 滚动id
     * @param pageSize 分页大小
     * @return 查询结果
     */
    @GetMapping(value = "/scrollQuery")
    public String scroll(@RequestParam  String scrollId, @RequestParam  Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            return "please input query page num";
        }
        NativeSearchQuery query = new NativeSearchQuery(new BoolQueryBuilder());
        query.setPageable(PageRequest.of(0, pageSize));
        SearchHits<ArticleEntity> searchHits;
        if (StringUtils.isEmpty(scrollId) || scrollId.equals("0")) {
            // 开启一个滚动查询,设置该scroll上下文存在60s
            // 同一个scroll上下文,只需要设置一次query(查询条件)
            searchHits = restTemplate.searchScrollStart(60000, query, ArticleEntity.class, IndexCoordinates.of("article"));
            if (searchHits instanceof SearchHitsImpl) {
                scrollId = ((SearchHitsImpl) searchHits).getScrollId();
            }
        } else {
            // 继续滚动
            searchHits = restTemplate.searchScrollContinue(scrollId, 60000, ArticleEntity.class, IndexCoordinates.of("article"));
        }
        List<ArticleEntity> articles = searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
        if (articles.isEmpty()) {
            // 结束滚动
            restTemplate.searchScrollClear(Collections.singletonList(scrollId));
            scrollId = null;
        }
        if (Objects.isNull(scrollId)) {
            Map<String, String> result = new HashMap<>(2);
            result.put("articles", articles.toString());
            result.put("message", "已到末尾");
            return result.toString();
        } else {
            Map<String, String> result = new HashMap<>();
            result.put("count", String.valueOf(searchHits.getTotalHits()));
            result.put("pageSize", String.valueOf(articles.size()));
            result.put("articles", articles.toString());
            result.put("scrollId", scrollId);
            return result.toString();
        }
    }
}