package com.example.shop_es.service;

import com.example.shop_common.entity.es.ArticleEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-08-22 21:43
 */
public interface ArticleRepository  extends ElasticsearchRepository<ArticleEntity,String> {
}
