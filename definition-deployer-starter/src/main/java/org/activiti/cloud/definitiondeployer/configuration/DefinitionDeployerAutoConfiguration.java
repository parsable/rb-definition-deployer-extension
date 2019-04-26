package org.activiti.cloud.definitiondeployer.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DefinitionDeployerProperties.class)
public class DefinitionDeployerAutoConfiguration {

    @Autowired
    private DefinitionDeployerProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public DefinitionDeployerConfig definitionDeployerConfig() {
        // when configuration parameters become relevant, set them here
        return new DefinitionDeployerConfig();
    }
}
