package com.parsable.rootstarter.autoconfigure;

import com.parsable.rootstarter.RootStarterConfig;
import com.parsable.rootstarter.RootStarterConfigParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RootStarterProperties.class)
public class RootStarterAutoConfiguration {

    @Autowired
    private RootStarterProperties rootStarterProperties;

    @Bean
    @ConditionalOnMissingBean
    public RootStarterConfig rootStarterConfig() {
        String userName = rootStarterProperties.getUserName() == null
                ? System.getProperty("user.name")
                : rootStarterProperties.getUserName();

        RootStarterConfig rootStarterConfig = new RootStarterConfig();
        rootStarterConfig.put(RootStarterConfigParams.USER_NAME, userName);

        return rootStarterConfig;
    }
}
