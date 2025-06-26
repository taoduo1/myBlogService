package com.example.shop_user.common.applicationrunner;

import com.example.shop_common.entity.base.ConfigTable;
import com.example.shop_user.service.ConfigTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class StartupRunner implements ApplicationRunner {



    @Autowired
    ConfigTableService configTableService;;

    @Override
    public void run(ApplicationArguments args) {
        //项目启动初始化
        ConfigTable data = configTableService.get(1);
    }
}
