/*
 * Copyright (c) 2008-2016, Orange, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.orange.oss.bosh.deployer.cfbroker;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import com.orange.oss.bosh.deployer.cfbroker.db.BindingRepository;
import com.orange.oss.bosh.deployer.cfbroker.db.InstanceBinding;

/**
 * Service Instance Binding broker API
 */
@Service
public class DeployerServiceInstanceBindingService implements ServiceInstanceBindingService {

	private static Logger logger=LoggerFactory.getLogger(DeployerServiceInstanceBindingService.class.getName());
	
	
	@Autowired
    private BindingRepository bindingRepository;
	


    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest
                req)  {

    	logger.info("Start binding service instance {}",req.getServiceInstanceId());    	
    	
        InstanceBinding instanceBinding = bindingRepository.findOne(req.getServiceInstanceId());
        if (instanceBinding != null) {
            throw new IllegalArgumentException("already exist:"+instanceBinding.toString());
        }

        final Map<String, Object> credentials = new HashMap<String, Object>();
        
        
        String ip="1.1.1.1";
		credentials.put("host", ip);
        credentials.put("InetAddress", ip);
        Integer port=2555;
		credentials.put("Port", port);
//
        String appGuid = req.getAppGuid();

        instanceBinding = new InstanceBinding(req.getBindingId(),req.getServiceInstanceId());
        
        this.bindingRepository.save(instanceBinding);
        
        //FIXME: 
        // generate dedicated credentials for binding (optional, otherwhise return service instance global credentials)
        // returns connectivity name
        
    	logger.info("Done binding service instance {}",req.getServiceInstanceId());        
    	CreateServiceInstanceBindingResponse resp=new CreateServiceInstanceBindingResponse();
        return new CreateServiceInstanceBindingResponse();
        
    }

    @Override
    public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest
           deleteServiceInstanceBindingRequest) {
    	
    	logger.info("Start delete binding service instance {}",deleteServiceInstanceBindingRequest.getServiceInstanceId());    	
        String id = deleteServiceInstanceBindingRequest.getBindingId();

        InstanceBinding instanceBinding = this.bindingRepository.findOne(id);
        if (instanceBinding != null) {
            this.bindingRepository.delete(instanceBinding);
        }
        //FIXME: delete credentials if per binding
        
    	logger.info("Done delete binding service instance {}",deleteServiceInstanceBindingRequest.getServiceInstanceId());        
    }
}
