package com.riteshbkadam.tms.controller;

import com.riteshbkadam.tms.dto.CreateTransporterRequest;
import com.riteshbkadam.tms.dto.TransporterResponse;
import com.riteshbkadam.tms.service.TransporterService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
@RequestMapping("/transporter")
public class TransporterController {

    private final TransporterService service;
    public TransporterController(TransporterService service) {
        this.service = service;
    }

    @PostMapping
    public TransporterResponse createTransporter(
            @RequestBody CreateTransporterRequest request
    ) {
        return service.createTransporter(request);
    }

}
