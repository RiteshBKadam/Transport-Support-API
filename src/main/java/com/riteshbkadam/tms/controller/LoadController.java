package com.riteshbkadam.tms.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
@RequestMapping("/load")
public class LoadController {

    @RequestMapping("/")
    public String home(){
        return "HIT the following APIs as per requirement";
    }

}
