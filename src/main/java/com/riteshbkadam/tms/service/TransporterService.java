package com.riteshbkadam.tms.service;

import com.riteshbkadam.tms.dto.*;
import com.riteshbkadam.tms.entity.Transporter;
import com.riteshbkadam.tms.repository.TransporterRepository;
import com.riteshbkadam.tms.exception.ResourceNotFoundException;
import com.riteshbkadam.tms.utils.AvailableTrucks;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransporterService {
    private final TransporterRepository repo;
    public TransporterService(TransporterRepository repo) { this.repo = repo; }
    @Transactional
    public TransporterResponse createTransporter(CreateTransporterRequest req) {
        Transporter t = new Transporter();
        t.setCompanyName(req.getCompanyName());
        t.setRating(req.getRating());
        if (req.getAvailableTrucks() != null) {
            List<AvailableTrucks> list = req.getAvailableTrucks().stream()
                    .map(r -> new AvailableTrucks(r.getTruckType(), r.getCount()))
                    .collect(Collectors.toList());
            t.setAvailableTrucks(list);
        }
        Transporter saved = repo.save(t);
        return new TransporterResponse(saved.getTransporterId(), saved.getCompanyName(), saved.getRating());
    }
    public TransporterResponse getTransporter(UUID id) {
        Transporter t = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transporter not found"));
        return new TransporterResponse(t.getTransporterId(), t.getCompanyName(), t.getRating());
    }
    @Transactional
    public String updateTrucks(UUID id, UpdateTrucksRequest req) {
        Transporter t = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transporter not found"));
        List<AvailableTrucks> list = req.availableTrucks().stream().map(r -> new AvailableTrucks(r.getTruckType(), r.getCount())).collect(Collectors.toList());
        t.setAvailableTrucks(list);
        repo.save(t);
        return "Updated";
    }
}
