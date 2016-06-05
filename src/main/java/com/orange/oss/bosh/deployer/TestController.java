package com.orange.oss.bosh.deployer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bosh")

public class TestController {
	private static Logger logger=LoggerFactory.getLogger(TestController.class.getName());
	
	@Autowired
	private BoshFeignClient client;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String execute() {
		logger.info("==> received \n {}");
		
		ApiMappings.Info info=client.getInfo();
	
		
		return "OK";
	}
}
