package com.example.shop_product.service;

import com.example.shop_common.common.response.ActionResult;
import com.example.shop_product.thirdto.User;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-07-07 22:06
 */
public interface UserService {

    ActionResult<User> getUserById(Integer id);
}
