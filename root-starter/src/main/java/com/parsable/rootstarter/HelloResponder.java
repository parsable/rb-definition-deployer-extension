package com.parsable.rootstarter;

import org.springframework.stereotype.Component;

@Component
public class HelloResponder {

    private RootStarterConfig config;

    public HelloResponder(RootStarterConfig config) {
        this.config = config;
    }

    public String hello() {
        String name = config.getProperty(RootStarterConfigParams.USER_NAME);
        return String.format("Hello to '%s' from the `HelloResponder` class\n", name);
    }
}
