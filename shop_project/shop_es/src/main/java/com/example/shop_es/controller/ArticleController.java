package com.example.shop_es.controller;

import com.example.shop_es.entity.ArticleEntity;
import com.example.shop_es.service.ArticleRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-08-22 21:43
 */
@RestController
@RequestMapping("/elk")
public class ArticleController {
    @Resource
    private ArticleRepository articleRepository;

    /**
     * 根据文档id查询数据
     *
     * @param id 文档id
     * @return 文档详情
     */
    @GetMapping("/byId")
    public String findById(@RequestParam String id) {
        Optional<ArticleEntity> record = articleRepository.findById(id);
        return  record.toString();
    }

    /**
     * 保存文档信息
     *
     * @param article 文档详情
     * @return 保存的文档信息
     */
    @PostMapping("/saveArticle")
    public String saveArticle(@RequestBody ArticleEntity article) {
        ArticleEntity result = articleRepository.save(article);
        return result.toString();
    }

    @DeleteMapping("/deleteById")
    public String deleteArticle(@RequestParam String id) {
        articleRepository.deleteById(id);
        return "success";
    }
}