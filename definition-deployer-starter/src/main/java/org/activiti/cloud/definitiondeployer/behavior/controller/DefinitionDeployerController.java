package org.activiti.cloud.definitiondeployer.behavior.controller;

import org.activiti.cloud.definitiondeployer.behavior.service.DefinitionDeployer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
public class DefinitionDeployerController {

    private static final String POST_MAPPING_VALUE = "/v1/extension/deploy-bpmn/{deploymentName}/{resourceName}";
    private static final String POST_MAPPING_CONSUMES = MediaType.APPLICATION_XML_VALUE;

    @Autowired
    DefinitionDeployer definitionDeployer;

    @PostMapping(value=POST_MAPPING_VALUE, consumes=POST_MAPPING_CONSUMES)
    public String deployBpmn(@PathVariable String deploymentName,
                             @PathVariable String resourceName,
                             @RequestBody String bpmnModelXml) {

        return definitionDeployer.deployProcessDefinition(deploymentName, resourceName, new ByteArrayInputStream(bpmnModelXml.getBytes()));
    }
}
