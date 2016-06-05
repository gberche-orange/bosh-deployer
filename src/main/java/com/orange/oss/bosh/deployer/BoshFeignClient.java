package com.orange.oss.bosh.deployer;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="bosh-client",url="${director.url}",configuration=com.orange.oss.bosh.deployer.FeignConfiguration.class)
public interface BoshFeignClient {

	//------------------------------
	@RequestMapping(method = RequestMethod.GET, value="/info")
	ApiMappings.Info getInfo();
	
	
	//-------------------------	
	@RequestMapping(method = RequestMethod.GET, value="/stemcells")
	List<ApiMappings.Stemcell> getStemcells();

	//-------------------------	
	
	@RequestMapping(method = RequestMethod.GET, value="/releases")
	List<ApiMappings.Release> getStemReleases();
	
	
	//-------------------------	
	@RequestMapping(method = RequestMethod.GET, value="/deployments")
	List<ApiMappings.Deployment> getDeployments();
	
	@RequestMapping(method = RequestMethod.GET, value="/deployments/{name}")
	ApiMappings.Deployment getDeployment(@PathVariable("name") String deploymentName);
	
	
	@RequestMapping(method = RequestMethod.POST, value="/deployments/{name}")
	//@RequestHeader(name="Content-Type",value="text/yaml")
	ApiMappings.Task  createupdateDeployment(@PathVariable("name") String deploymentName,
						@RequestBody String manifest,
						@RequestParam("recreate") Boolean recreate,
						@RequestParam("skipDrain") String skipDrain);

	@RequestMapping(method = RequestMethod.DELETE, value="/deployments/{name}")
	ApiMappings.Task deleteDeployments(@PathVariable("name") String name, @RequestParam("force") boolean force);
	
}
