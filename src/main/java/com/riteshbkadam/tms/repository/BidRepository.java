package com.riteshbkadam.tms.repository;

import com.riteshbkadam.tms.entity.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BidRepository extends JpaRepository<Transporter, UUID> {
}

