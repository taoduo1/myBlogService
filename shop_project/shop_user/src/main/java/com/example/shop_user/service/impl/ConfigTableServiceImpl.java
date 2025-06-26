package com.example.shop_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shop_common.common.constant.NormalConstant;
import com.example.shop_common.common.service.CrudServiceImpl;
import com.example.shop_common.entity.base.ConfigTable;
import com.example.shop_user.common.redis.annotation.RedisCacheDelete;
import com.example.shop_user.common.redis.annotation.RedisCacheGet;
import com.example.shop_user.common.redis.annotation.RedisCachePut;
import com.example.shop_user.mapper.ConfigTableMapper;
import com.example.shop_user.service.ConfigTableService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author duo.tao
 * @since 2022-06-13
 */
@Service
public class ConfigTableServiceImpl extends CrudServiceImpl<ConfigTableMapper, ConfigTable> implements ConfigTableService {

    @Deprecated
    @RedisCachePut(key = NormalConstant.REDIS_CONFIG_KEY, minutes = 3 * 60)
    @Override
    public void setConfigKey(String configType , String configKey, ConfigTable user) {

    }
    @RedisCacheGet(key = NormalConstant.REDIS_CONFIG_KEY, minutes = 3 * 60)
    @Override
    public ConfigTable getConfigKey(String configType , String configKey) {
        return dao.selectOne(new QueryWrapper<ConfigTable>().eq("config_type",configType).eq("config_key",configKey));
    }

    @RedisCacheDelete(key = NormalConstant.REDIS_CONFIG_KEY)
    @Override
    public void cleanConfigKeyCache(String configType , String configKey) {

    }
}
