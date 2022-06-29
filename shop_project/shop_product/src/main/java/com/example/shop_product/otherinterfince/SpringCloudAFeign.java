package com.example.shop_product.otherinterfince;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient("user-server")
public interface SpringCloudAFeign {

	@RequestMapping(value = "/user/findById/1")
	public String getStr();
}
