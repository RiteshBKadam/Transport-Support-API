package com.riteshbkadam.tms.controller;

import com.riteshbkadam.tms.dto.*;
import com.riteshbkadam.tms.service.TransporterService;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/transporter")
public class TransporterController {
    private final TransporterService service;
    public TransporterController(TransporterService service) { this.service = service; }
    @PostMapping
    public TransporterResponse createTransporter(@RequestBody CreateTransporterRequest req) { return service.createTransporter(req); }
    @GetMapping("/{id}")
    public TransporterResponse getTransporter(@PathVariable UUID id) { return service.getTransporter(id); }
    @PutMapping("/{id}/trucks")
    public String updateTrucks(@PathVariable UUID id, @RequestBody UpdateTrucksRequest req) { return service.updateTrucks(id, req); }
}
