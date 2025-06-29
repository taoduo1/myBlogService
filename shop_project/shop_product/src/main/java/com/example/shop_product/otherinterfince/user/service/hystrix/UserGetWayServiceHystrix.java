package com.example.shop_product.otherinterfince.user.service.hystrix;

import com.example.shop_common.common.response.ActionResult;
import com.example.shop_product.otherinterfince.user.service.UserGetWayService;
import com.example.shop_product.thirdto.User;
import org.springframework.stereotype.Service;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-07-08 21:40
 */

@Service
public class UserGetWayServiceHystrix implements UserGetWayService {

    @Override
    public ActionResult<User> getStr(Integer id) {
        return null;
    }
}
