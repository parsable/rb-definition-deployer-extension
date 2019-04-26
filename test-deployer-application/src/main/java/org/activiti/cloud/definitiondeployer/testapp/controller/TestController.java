package org.activiti.cloud.definitiondeployer.testapp.controller;

import org.activiti.cloud.definitiondeployer.behavior.DefinitionDeployer;
import org.activiti.cloud.definitiondeployer.behavior.DefinitionDeployerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
public class TestController {

    @Autowired
    DefinitionDeployer definitionDeployer;

    @PostMapping(value=DefinitionDeployerEndpoint.POST_MAPPING_VALUE, consumes=DefinitionDeployerEndpoint.POST_MAPPING_CONSUMES)
    public String deployBpmn(@PathVariable String deploymentName,
                             @PathVariable String resourceName,
                             @RequestBody String bpmnModelXml) {
        return definitionDeployer.deployProcessDefinition(deploymentName, resourceName, new ByteArrayInputStream(bpmnModelXml.getBytes()));
    }
}
