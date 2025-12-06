package com.riteshbkadam.tms.service;

import com.riteshbkadam.tms.dto.*;
import com.riteshbkadam.tms.entity.Bid;
import com.riteshbkadam.tms.entity.Load;
import com.riteshbkadam.tms.repository.BidRepository;
import com.riteshbkadam.tms.repository.LoadRepository;
import com.riteshbkadam.tms.utils.status.LoadStatus;
import com.riteshbkadam.tms.exception.ResourceNotFoundException;
import com.riteshbkadam.tms.exception.InvalidStatusTransitionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoadService {
    private final LoadRepository repo;
    private final BidRepository bidRepo;
    public LoadService(LoadRepository repo, BidRepository bidRepo) { this.repo = repo; this.bidRepo = bidRepo; }
    @Transactional
    public LoadResponse createLoad(CreateLoadRequest req) {
        Load l = new Load();
        l.setShipperId(req.getShipperId());
        l.setLoadingCity(req.getLoadingCity());
        l.setUnloadingCity(req.getUnloadingCity());
        l.setLoadingDate(req.getLoadingDate());
        l.setProductType(req.getProductType());
        l.setWeight(req.getWeight());
        l.setWeightUnit(req.getWeightUnit());
        l.setTruckType(req.getTruckType());
        l.setNoOfTrucks(req.getNoOfTrucks());
        l.setStatus(LoadStatus.POSTED);
        l.setDatePosted(Timestamp.from(Instant.now()));
        Load saved = repo.save(l);
        return toResponse(saved);
    }
    public Page<LoadResponse> getLoads(String shipperId, LoadStatus status, int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        Page<Load> p;
        if (shipperId != null && status != null) p = repo.findByShipperIdAndStatus(shipperId, status, pr);
        else if (shipperId != null) p = repo.findByShipperId(shipperId, pr);
        else if (status != null) p = repo.findByStatus(status, pr);
        else p = repo.findAll(pr);
        return p.map(this::toResponse);
    }
    public LoadDetailsResponse getLoadWithBids(UUID loadId) {
        Load l = repo.findById(loadId).orElseThrow(() -> new ResourceNotFoundException("Load not found"));
        List<Bid> bids = bidRepo.findByLoadId(loadId);
        List<BidResponse> br = bids.stream().map(this::toBidResponse).collect(Collectors.toList());
        return new LoadDetailsResponse(l.getLoadId(), l.getShipperId(), l.getLoadingCity(), l.getUnloadingCity(), l.getProductType(), l.getWeight(), l.getWeightUnit(), l.getLoadingDate(), l.getTruckType(), l.getNoOfTrucks(), l.getStatus().name(), l.getDatePosted(), br);
    }
    @Transactional
    public String cancelLoad(UUID loadId) {
        Load l = repo.findById(loadId).orElseThrow(() -> new ResourceNotFoundException("Load not found"));
        if (l.getStatus() == LoadStatus.BOOKED) throw new InvalidStatusTransitionException("Cannot cancel booked load");
        l.setStatus(LoadStatus.CANCELLED);
        repo.save(l);
        return "Load cancelled";
    }
    public List<BidResponse> getBestBids(UUID loadId) {
        Load l = repo.findById(loadId).orElseThrow(() -> new ResourceNotFoundException("Load not found"));
        List<Bid> bids = bidRepo.findByLoadIdAndStatus(loadId, com.riteshbkadam.tms.utils.status.BidStatus.PENDING);
        List<BidResponse> responses = bids.stream().map(this::toBidResponse).collect(Collectors.toList());
        responses.sort((a,b) -> Double.compare(score(b), score(a)));
        return responses;
    }
    private double score(BidResponse b) {
        double rateScore = 1.0 / b.getProposedRate();
        double rating = 0.0;
        return rateScore * 0.7 + (rating / 5.0) * 0.3;
    }
    private LoadResponse toResponse(Load l) {
        return new LoadResponse(l.getLoadId(), l.getShipperId(), l.getLoadingCity(), l.getUnloadingCity(), l.getProductType(), l.getWeight(), l.getWeightUnit(), l.getLoadingDate(), l.getTruckType(), l.getNoOfTrucks(), l.getStatus().name(), l.getDatePosted());
    }
    private BidResponse toBidResponse(Bid b) {
        return new BidResponse(b.getBidId(), b.getLoadId(), b.getTransporterId(), b.getProposedRate(), b.getTrucksOffered(), b.getStatus().name(), b.getSubmittedAt());
    }
}
