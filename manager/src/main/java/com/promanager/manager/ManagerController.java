package com.promanager.manager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerController {
    
    @RequestMapping("/manager")
    public String manager(){
        return "Hello World";
    }
}
