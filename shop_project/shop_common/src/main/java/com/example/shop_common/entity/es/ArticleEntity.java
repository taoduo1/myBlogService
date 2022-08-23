package com.example.shop_common.entity.es;

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

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createTime = new Date();


}
