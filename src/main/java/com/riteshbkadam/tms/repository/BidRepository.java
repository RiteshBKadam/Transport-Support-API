package com.riteshbkadam.tms.repository;

import com.riteshbkadam.tms.entity.Bid;
import com.riteshbkadam.tms.utils.status.BidStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BidRepository extends JpaRepository<Bid, UUID> {
    List<Bid> findByLoadId(UUID loadId);
    List<Bid> findByLoadIdAndStatus(UUID loadId, BidStatus status);
    List<Bid> findByTransporterId(UUID transporterId);
    List<Bid> findByTransporterIdAndStatus(UUID transporterId, BidStatus status);
    List<Bid> findByLoadIdAndTransporterId(UUID loadId, UUID transporterId);
}
