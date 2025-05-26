package com.example.shop_job.jobhandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class JobHandler {

    @XxlJob("testJobHandler")
    public void demoJobHandler() throws Exception {
        System.out.printf("hello word");
    }
}
