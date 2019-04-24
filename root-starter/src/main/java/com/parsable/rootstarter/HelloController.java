package com.parsable.rootstarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloResponder responder;

    @RequestMapping("/hello")
    public String hello() {
        return responder.hello();
    }
}
