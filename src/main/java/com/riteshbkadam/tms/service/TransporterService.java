package com.riteshbkadam.tms.service;

import com.riteshbkadam.tms.dto.CreateTransporterRequest;
import com.riteshbkadam.tms.dto.TransporterResponse;
import com.riteshbkadam.tms.entity.Transporter;
import com.riteshbkadam.tms.repository.TransporterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransporterService {
    @Autowired
    private TransporterRepository repo;
    public TransporterService(TransporterRepository repo) {
        this.repo = repo;
    }
    public TransporterResponse createTransporter(CreateTransporterRequest req) {

        Transporter t = new Transporter();
        t.setCompanyName(req.getCompanyName());
        t.setRating(req.getRating());
        t.setAvailableTrucks(req.getAvailableTrucks());

        Transporter saved = repo.save(t);

        return new TransporterResponse(
                saved.getTransporterId(),
                saved.getCompanyName(),
                saved.getRating()
        );
    }
}
