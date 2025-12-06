package com.riteshbkadam.tms.service;

import com.riteshbkadam.tms.dto.*;
import com.riteshbkadam.tms.entity.Booking;
import com.riteshbkadam.tms.entity.Bid;
import com.riteshbkadam.tms.entity.Load;
import com.riteshbkadam.tms.entity.Transporter;
import com.riteshbkadam.tms.repository.BookingRepository;
import com.riteshbkadam.tms.repository.BidRepository;
import com.riteshbkadam.tms.repository.LoadRepository;
import com.riteshbkadam.tms.repository.TransporterRepository;
import com.riteshbkadam.tms.exception.*;
import com.riteshbkadam.tms.utils.AvailableTrucks;
import com.riteshbkadam.tms.utils.status.BookingStatus;
import com.riteshbkadam.tms.utils.status.BidStatus;
import com.riteshbkadam.tms.utils.status.LoadStatus;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository bookingRepo;
    private final BidRepository bidRepo;
    private final LoadRepository loadRepo;
    private final TransporterRepository transporterRepo;
    public BookingService(BookingRepository bookingRepo, BidRepository bidRepo, LoadRepository loadRepo, TransporterRepository transporterRepo) {
        this.bookingRepo = bookingRepo;
        this.bidRepo = bidRepo;
        this.loadRepo = loadRepo;
        this.transporterRepo = transporterRepo;
    }
    @Transactional
    public BookingResponse createBooking(CreateBookingRequest req) {
        Bid b = bidRepo.findById(req.getBidId()).orElseThrow(() -> new ResourceNotFoundException("Bid not found"));
        if (b.getStatus() != BidStatus.PENDING) throw new InvalidStatusTransitionException("Bid not pending");
        Load l = loadRepo.findById(b.getLoadId()).orElseThrow(() -> new ResourceNotFoundException("Load not found"));
        Transporter t = transporterRepo.findById(b.getTransporterId()).orElseThrow(() -> new ResourceNotFoundException("Transporter not found"));
        int avail = t.getAvailableTrucks().stream().filter(a -> a.getTruckType().equals(l.getTruckType())).mapToInt(AvailableTrucks::getCount).sum();
        if (b.getTrucksOffered() > avail) throw new InsufficientCapacityException("Insufficient trucks");
        try {
            t.getAvailableTrucks().forEach(at -> { if (at.getTruckType().equals(l.getTruckType())) at.setCount(at.getCount() - b.getTrucksOffered()); });
            transporterRepo.save(t);
            b.setStatus(BidStatus.ACCEPTED);
            bidRepo.save(b);
            Booking booking = new Booking();
            booking.setLoadId(l.getLoadId());
            booking.setBidId(b.getBidId());
            booking.setTransporterId(b.getTransporterId());
            booking.setAllocatedTrucks(b.getTrucksOffered());
            booking.setFinalRate(b.getProposedRate());
            booking.setStatus(BookingStatus.CONFIRMED);
            booking.setBookedAt(Timestamp.from(Instant.now()));
            Booking saved = bookingRepo.save(booking);
            int sumAllocated = bookingRepo.findAll().stream().filter(x -> x.getLoadId().equals(l.getLoadId()) && x.getStatus() == BookingStatus.CONFIRMED).mapToInt(Booking::getAllocatedTrucks).sum();
            if (sumAllocated >= l.getNoOfTrucks()) {
                l.setStatus(LoadStatus.BOOKED);
            } else {
                if (l.getStatus() == null || l.getStatus() == LoadStatus.POSTED) l.setStatus(LoadStatus.OPEN_FOR_BIDS);
            }
            loadRepo.save(l);
            return toBookingResponse(saved);
        } catch (OptimisticLockException ole) {
            throw new ConflictException("Concurrent modification");
        }
    }
    public BookingResponse getBooking(UUID id) {
        Booking b = bookingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return toBookingResponse(b);
    }
    @Transactional
    public String cancelBooking(UUID bookingId) {
        Booking b = bookingRepo.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        if (b.getStatus() == BookingStatus.CANCELLED) return "Already cancelled";
        b.setStatus(BookingStatus.CANCELLED);
        bookingRepo.save(b);
        Transporter t = transporterRepo.findById(b.getTransporterId()).orElseThrow(() -> new ResourceNotFoundException("Transporter not found"));
        Load l = loadRepo.findById(b.getLoadId()).orElseThrow(() -> new ResourceNotFoundException("Load not found"));
        t.getAvailableTrucks().forEach(at -> { if (at.getTruckType().equals(l.getTruckType())) at.setCount(at.getCount() + b.getAllocatedTrucks()); });
        transporterRepo.save(t);
        int sumAllocated = bookingRepo.findAll().stream().filter(x -> x.getLoadId().equals(l.getLoadId()) && x.getStatus() == BookingStatus.CONFIRMED).mapToInt(Booking::getAllocatedTrucks).sum();
        if (sumAllocated == 0 && l.getStatus() == LoadStatus.BOOKED) l.setStatus(LoadStatus.OPEN_FOR_BIDS);
        loadRepo.save(l);
        return "Booking cancelled";
    }
    private BookingResponse toBookingResponse(Booking b) {
        return new BookingResponse(b.getBookingId(), b.getLoadId(), b.getBidId(), b.getTransporterId(), b.getAllocatedTrucks(), b.getFinalRate(), b.getStatus().name(), b.getBookedAt());
    }
}
