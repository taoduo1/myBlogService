package com.example.shop_product.otherinterfince;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient("user-server")
public interface UserServiceAFeign {

	@RequestMapping(value = "/user/user/findById/{id}")
	String getStr(@PathVariable("id") Integer id);
}
