package com.example.shop_user.service;

import com.example.shop_common.common.service.CrudService;
import com.example.shop_common.entity.base.ConfigTable;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author duo.tao
 * @since 2022-06-13
 */
public interface ConfigTableService extends CrudService<ConfigTable> {

    void setConfigKey(String configType, String configKey, ConfigTable user);

    ConfigTable getConfigKey(String configType, String configKey);

    void cleanConfigKeyCache(String configType, String configKey);
}
