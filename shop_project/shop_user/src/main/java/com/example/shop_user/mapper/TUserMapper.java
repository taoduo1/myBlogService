package com.example.shop_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shop_user.entity.TUser;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author duo.tao
 * @since 2022-02-25
 */
public interface TUserMapper extends BaseMapper<TUser> {

     List<TUser> findAllUser();

}
