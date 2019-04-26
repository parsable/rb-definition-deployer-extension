package org.activiti.cloud.definitiondeployer.behavior;

import org.springframework.http.MediaType;

public class DefinitionDeployerEndpoint {
    public static final String POST_MAPPING_VALUE = "/v1/extension/deploy-bpmn/{deploymentName}/{resourceName}";
    public static final String POST_MAPPING_CONSUMES = MediaType.APPLICATION_XML_VALUE;
}
