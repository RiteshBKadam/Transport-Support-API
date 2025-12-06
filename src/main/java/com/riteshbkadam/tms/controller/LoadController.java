package com.riteshbkadam.tms.controller;

import com.riteshbkadam.tms.dto.*;
import com.riteshbkadam.tms.service.LoadService;
import com.riteshbkadam.tms.utils.status.LoadStatus;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/load")
public class LoadController {
    private final LoadService service;
    public LoadController(LoadService service) { this.service = service; }
    @PostMapping
    public LoadResponse createLoad(@RequestBody CreateLoadRequest req) { return service.createLoad(req); }
    @GetMapping
    public Page<LoadResponse> getLoads(@RequestParam(required = false) String shipperId, @RequestParam(required = false) LoadStatus status, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) { return service.getLoads(shipperId, status, page, size); }
    @GetMapping("/{loadId}")
    public LoadDetailsResponse getLoad(@PathVariable UUID loadId) { return service.getLoadWithBids(loadId); }
    @PatchMapping("/{loadId}/cancel")
    public String cancelLoad(@PathVariable UUID loadId) { return service.cancelLoad(loadId); }
    @GetMapping("/{loadId}/best-bids")
    public List<BidResponse> bestBids(@PathVariable UUID loadId) { return service.getBestBids(loadId); }
}
