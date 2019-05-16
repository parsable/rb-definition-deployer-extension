package org.activiti.cloud.definitiondeployer.testapp;

import org.activiti.cloud.api.process.model.CloudProcessDefinition;
import org.activiti.cloud.definitiondeployer.behavior.service.DefinitionDeployer;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.h2.store.fs.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StreamUtils;

import java.net.http.HttpHeaders;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
@DirtiesContext
public class DefinitionDeployerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private KeycloackTokenProcuder

    @Value("classpath:/org/activiti/cloud/definitiondeployer/TestProcess.bpmn20.xml")
    Resource bpmnFile;

    @Test
    public void testDeployProcessDefinition() throws IOException {
        // given

        keycloakSecurityContextClientRequestInterceptor.setKeycloakTestUser("hruser");

        String deploymentName = "test-deployment";
        String resourceName = "TestProcess.bpmn20.xml"; // must have .bpmn or .bpmn20.xml suffix to trigger deployment
        String deployUrl = "/v1/extension/deploy-bpmn/" + deploymentName + "/" + resourceName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/xml");
        String bpmnXml = StreamUtils.copyToString(bpmnFile.getInputStream(), Charset.defaultCharset());

        HttpEntity<String> requestBody = new HttpEntity(bpmnXml, headers);

        //when
        String deploymentId = restTemplate.exchange(deployUrl, HttpMethod.POST, requestBody, String.class);

        // then
        assertThat(deploymentId).isNotNull();

        // and when
        List<ProcessDefinition> processDefinitions = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .list();

        // then
        assertThat(processDefinitions).hasSize(1);

        // given
        ProcessDefinition processDefinition = processDefinitions.get(0);

        // when
        ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
                .processDefinitionId(processDefinition.getId())
                .name("test-process-instance")
                .start();

        // then
        assertThat(runtimeService.createProcessInstanceQuery().list()).hasSize(1);

        // and given
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        // when
        taskService.complete(task.getId());

        // then
        assertThat(taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult()).isNull();

        // Cloud connector service should be active
        assertThat(runtimeService.createProcessInstanceQuery().list()).hasSize(1);
    }
}
