package com.riteshbkadam.tms.controller;

import com.riteshbkadam.tms.dto.*;
import com.riteshbkadam.tms.service.BookingService;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService service;
    public BookingController(BookingService service) { this.service = service; }
    @PostMapping
    public BookingResponse createBooking(@RequestBody CreateBookingRequest req) { return service.createBooking(req); }
    @GetMapping("/{bookingId}")
    public BookingResponse getBooking(@PathVariable UUID bookingId) { return service.getBooking(bookingId); }
    @PatchMapping("/{bookingId}/cancel")
    public String cancelBooking(@PathVariable UUID bookingId) { return service.cancelBooking(bookingId); }
}
