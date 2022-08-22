package com.example.shop_es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-08-22 21:42
 */
@Document(indexName = "article")
@Data
public class ArticleEntity {

    @Id
    private String id;

    private String title;

    private String content;

    private Integer userId;

    private Date createTime;
}
