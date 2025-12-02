package com.riteshbkadam.tms.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
@RequestMapping("/bid")
public class BidService {

    @RequestMapping("/")
    public String home(){
        return "HIT the following APIs as per requirement";
    }

}
