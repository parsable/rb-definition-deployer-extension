package com.parsable.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.parsable.rootstarter.HelloResponder;

@RestController
public class AppBasicHelloController {

    @Autowired
    private HelloResponder responder;

    @RequestMapping("/appbasichello")
    public String appHello() {
        return responder.hello();
    }
}
