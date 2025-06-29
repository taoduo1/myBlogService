package com.example.shop_product.otherinterfince.user.service;

import com.example.shop_common.common.response.ActionResult;
import com.example.shop_product.otherinterfince.user.service.hystrix.UserGetWayServiceHystrix;
import com.example.shop_product.thirdto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-07-11 22:05
 */
@Service
@FeignClient(name = "gateway-server", fallback = UserGetWayServiceHystrix.class)
public interface UserGetWayService {

    @GetMapping(value = "/api/user/user/findById/{id}")
    ActionResult<User> getStr(@PathVariable("id") Integer id);
}
