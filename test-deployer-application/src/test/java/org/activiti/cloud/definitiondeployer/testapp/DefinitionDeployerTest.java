package org.activiti.cloud.definitiondeployer.testapp;

import org.activiti.cloud.definitiondeployer.behavior.service.DefinitionDeployer;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
@DirtiesContext
public class DefinitionDeployerTest {

    @Autowired
    private DefinitionDeployer definitionDeployer;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Value("classpath:/org/activiti/cloud/definitiondeployer/TestProcess.bpmn20.xml")
    Resource bpmnFile;

    @Test
    public void testDeployProcessDefinition() throws IOException {
        // given
        String deploymentName = "test-deployment";
        String resourceName = "TestProcess.bpmn20.xml"; // must have .bpmn or .bpmn20.xml suffix to trigger deployment

        // when
        String deploymentId = definitionDeployer.deployProcessDefinition(deploymentName, resourceName, bpmnFile.getInputStream());

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
