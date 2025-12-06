package com.riteshbkadam.tms.controller;

import com.riteshbkadam.tms.dto.*;
import com.riteshbkadam.tms.service.BidService;
import com.riteshbkadam.tms.utils.status.BidStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bid")
public class BidController {
    private final BidService service;
    public BidController(BidService service) {
        this.service = service;
    }
    @PostMapping
    public BidResponse createBid(@RequestBody CreateBidRequest req) {
        return service.createBid(req);
    }
    @GetMapping
    public List<BidResponse> getBids(@RequestParam(required = false) UUID loadId,
                                     @RequestParam(required = false) UUID transporterId,
                                     @RequestParam(required = false) BidStatus status) {
        return service.getBids(loadId, transporterId, status); }
    @GetMapping("/{bidId}")
    public BidResponse getBid(@PathVariable UUID bidId) {
        return service.getBid(bidId);
    }

    @PatchMapping("/{bidId}/reject")
    public String rejectBid(@PathVariable UUID bidId) {
        return service.rejectBid(bidId);
    }
}
