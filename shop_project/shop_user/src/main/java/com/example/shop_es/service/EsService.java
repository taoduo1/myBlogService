package com.example.shop_es.service;

import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.entity.es.ArticleEntity;
import com.example.shop_es.service.impl.EsServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-07-11 22:05
 */
@Service
@FeignClient(name = "es-service", fallback = EsServiceHystrix.class)
public interface EsService {

    @RequestMapping(value = "/api/es/findById/{id}")
    ActionResult<ArticleEntity> findById(@RequestParam String id);

    @PostMapping(value = "/api/es/saveArticle")
    ActionResult<String> saveArticle(@RequestBody String data);

    @PostMapping(value = "/api/es/deleteArticle")
    ActionResult<String> deleteArticle(@RequestParam String id);
}
