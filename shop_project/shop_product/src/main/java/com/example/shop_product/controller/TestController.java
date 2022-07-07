package com.example.shop_product.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	/*@Resource
	private RestTemplate restTemplate;*/



	/*@RequestMapping(value = "/getStr")
	public String getStr(){
		//调用远程服务,这里是使用应用名spring-cloud-b，在application.properties里配置
		String str = restTemplate.getForObject("http://user-server/user/findById/1",String.class);
		return str;
	}*/

	/*@Resource
	private UserServiceAFeign userServiceAFeign;

	@RequestMapping("/getStr2")
	public String getStr2(){
		String str = userServiceAFeign.getStr(1);
		return str;
	}*/
}
