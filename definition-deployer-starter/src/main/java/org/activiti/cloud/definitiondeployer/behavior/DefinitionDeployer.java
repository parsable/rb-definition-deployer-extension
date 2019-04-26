package org.activiti.cloud.definitiondeployer.behavior;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class DefinitionDeployer {

    private final RepositoryService repositoryService;

    public DefinitionDeployer(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public String deployProcessDefinition(String deploymentName, String resourceName, InputStream bpmnXmlStream) {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .name(deploymentName)
                .addInputStream(resourceName, bpmnXmlStream);

        Deployment deployment = deploymentBuilder.deploy();

        return deployment.getId();
    }
}
