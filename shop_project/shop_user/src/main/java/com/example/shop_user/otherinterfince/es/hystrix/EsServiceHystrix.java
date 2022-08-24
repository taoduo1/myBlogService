package com.example.shop_user.otherinterfince.es.hystrix;

import com.example.shop_common.common.response.ActionResult;
import com.example.shop_common.common.response.ErrorInfo;
import com.example.shop_common.entity.es.ArticleEntity;
import com.example.shop_common.utils.ResultUtil;
import com.example.shop_user.otherinterfince.es.EsService;
import org.springframework.stereotype.Service;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-07-08 21:40
 */

@Service
public class EsServiceHystrix implements EsService {

    @Override
    public ActionResult<ArticleEntity> findById(String id) {
        return ResultUtil.fail(new ErrorInfo("服务异常"));
    }

    @Override
    public ActionResult<String> saveArticle(String data) {
        return ResultUtil.fail(new ErrorInfo("服务异常"));
    }

    @Override
    public ActionResult<String> deleteArticle(String id) {
        return ResultUtil.fail(new ErrorInfo("服务异常"));
    }
}
