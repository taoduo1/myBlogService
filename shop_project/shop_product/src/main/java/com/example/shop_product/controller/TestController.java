package com.example.shop_product.controller;

import com.example.shop_product.otherinterfince.SpringCloudAFeign;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class TestController {

	@Resource
	private RestTemplate restTemplate;

	@Resource
	private SpringCloudAFeign springCloudAFeign;

	@RequestMapping(value = "/getStr")
	public String getStr(){
		//调用远程服务,这里是使用应用名spring-cloud-b，在application.properties里配置
		String str = restTemplate.getForObject("http://user-server/user/findById/1",String.class);
		return str;
	}

	@RequestMapping("/getStr2")
	public String getStr2(){
		String str = springCloudAFeign.getStr(1);
		return str;
	}
}
