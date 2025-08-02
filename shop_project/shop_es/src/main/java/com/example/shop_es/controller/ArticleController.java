package com.example.shop_es.controller;

import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.common.response.ErrorInfo;
import com.example.shop_common.entity.es.ArticleEntity;
import com.example.shop_common.utils.DataUtil;
import com.example.shop_common.utils.ResultUtil;
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
    public ActionResult<ArticleEntity> findById(@RequestParam String id) {
        Optional<ArticleEntity> record = articleRepository.findById(id);
        if (record.isEmpty()){
            return ResultUtil.ok();
        }
        return ResultUtil.ok(record.get());
    }

    /**
     * 保存文档信息
     *
     * @param article 文档详情
     * @return 保存的文档信息
     */
    @PostMapping("/saveArticle")
    public ActionResult<String> saveArticle(@RequestBody ArticleEntity  article) {
        if (DataUtil.isNull(article)){
            return ResultUtil.fail(new ErrorInfo("数据转换失败，请检查！"));
        }
        articleRepository.save(article);
        return ResultUtil.ok();
    }

    @DeleteMapping("/deleteById")
    public ActionResult<String> deleteArticle(@RequestParam String id) {
        articleRepository.deleteById(id);
        return ResultUtil.ok();
    }
}