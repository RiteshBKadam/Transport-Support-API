package com.riteshbkadam.tms.service;

import com.riteshbkadam.tms.dto.*;
import com.riteshbkadam.tms.entity.Bid;
import com.riteshbkadam.tms.entity.Load;
import com.riteshbkadam.tms.entity.Transporter;
import com.riteshbkadam.tms.repository.BidRepository;
import com.riteshbkadam.tms.repository.LoadRepository;
import com.riteshbkadam.tms.repository.TransporterRepository;
import com.riteshbkadam.tms.exception.ResourceNotFoundException;
import com.riteshbkadam.tms.exception.InsufficientCapacityException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BidService {
    private final BidRepository bidRepo;
    private final LoadRepository loadRepo;
    private final TransporterRepository transporterRepo;
    public BidService(BidRepository bidRepo, LoadRepository loadRepo, TransporterRepository transporterRepo) { this.bidRepo = bidRepo; this.loadRepo = loadRepo; this.transporterRepo = transporterRepo; }
    @Transactional
    public BidResponse createBid(CreateBidRequest req) {
        Load l = loadRepo.findById(req.getLoadId()).orElseThrow(() -> new ResourceNotFoundException("Load not found"));
        if (l.getStatus() == null || l.getStatus().name().equals("CANCELLED") || l.getStatus().name().equals("BOOKED")) throw new InsufficientCapacityException("Cannot bid on cancelled or booked loads");
        Transporter t = transporterRepo.findById(req.getTransporterId()).orElseThrow(() -> new ResourceNotFoundException("Transporter not found"));
        int avail = t.getAvailableTrucks().stream().filter(a -> a.getTruckType().equals(l.getTruckType())).mapToInt(a -> a.getCount()).sum();
        if (req.getTrucksOffered() > avail) throw new InsufficientCapacityException("Insufficient trucks");
        Bid b = new Bid();
        b.setLoadId(req.getLoadId());
        b.setTransporterId(req.getTransporterId());
        b.setProposedRate(req.getProposedRate());
        b.setTrucksOffered(req.getTrucksOffered());
        b.setStatus(com.riteshbkadam.tms.utils.status.BidStatus.PENDING);
        b.setSubmittedAt(Timestamp.from(Instant.now()));
        if (bidRepo.findByLoadId(req.getLoadId()).isEmpty()) {
            l.setStatus(com.riteshbkadam.tms.utils.status.LoadStatus.OPEN_FOR_BIDS);
            loadRepo.save(l);
        }
        Bid saved = bidRepo.save(b);
        return toBidResponse(saved);
    }
    public List<BidResponse> getBids(UUID loadId, UUID transporterId, com.riteshbkadam.tms.utils.status.BidStatus status) {
        List<Bid> list;
        if (loadId != null && transporterId != null) list = bidRepo.findByLoadIdAndTransporterId(loadId, transporterId);
        else if (loadId != null) list = (status == null) ? bidRepo.findByLoadId(loadId) : bidRepo.findByLoadIdAndStatus(loadId, status);
        else if (transporterId != null) list = (status == null) ? bidRepo.findByTransporterId(transporterId) : bidRepo.findByTransporterIdAndStatus(transporterId, status);
        else list = bidRepo.findAll();
        return list.stream().map(this::toBidResponse).collect(Collectors.toList());
    }
    public BidResponse getBid(UUID bidId) {
        Bid b = bidRepo.findById(bidId).orElseThrow(() -> new ResourceNotFoundException("Bid not found"));
        return toBidResponse(b);
    }
    @Transactional
    public String rejectBid(UUID bidId) {
        Bid b = bidRepo.findById(bidId).orElseThrow(() -> new ResourceNotFoundException("Bid not found"));
        b.setStatus(com.riteshbkadam.tms.utils.status.BidStatus.REJECTED);
        bidRepo.save(b);
        return "Bid rejected";
    }
    private BidResponse toBidResponse(Bid b) {
        return new BidResponse(b.getBidId(), b.getLoadId(), b.getTransporterId(), b.getProposedRate(), b.getTrucksOffered(), b.getStatus().name(), b.getSubmittedAt());
    }
}
