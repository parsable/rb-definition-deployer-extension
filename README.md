# Activiti Cloud Runtime Bundle Starter :: Definition Deployer

### Origin disclosure

The code for this starter came pretty much directly from [this commit](https://github.com/introproventures/example-runtime-bundle/commit/707708cae17a7d4e5b4de312ec83779dd024134f) by [igdianov](https://github.com/introproventures/example-runtime-bundle/commits?author=igdianov) for [IntroPro Ventures](https://github.com/introproventures).
All I did was package it in a starter.

## Project Contents

1. `definition-deployer-starter`

This is the starter you'll use if you want to add the ability to deploy new Process Definitions to your Runtime Bundle.
Include it as you would any other starter.

2. `test-deployer-application`

This is an app that shows how to import the starter and use it in your code.
In really terse terms, you need a `@RestController`-annotated class with the following content. Take note of the Annotated method arguments.

```java
    @Autowired
    DefinitionDeployer definitionDeployer;

    @PostMapping(value=DefinitionDeployerEndpoint.POST_MAPPING_VALUE, consumes=DefinitionDeployerEndpoint.POST_MAPPING_CONSUMES)
    public String deployBpmn(@PathVariable String deploymentName,
                             @PathVariable String resourceName,
                             @RequestBody String bpmnModelXml) {
        return definitionDeployer.deployProcessDefinition(deploymentName, resourceName, new ByteArrayInputStream(bpmnModelXml.getBytes()));
```


There's one test: `org.activiti.cloud.definitiondeployer.testapp.DefinitionDeployerTest.testDeployProcessDefinition()`.