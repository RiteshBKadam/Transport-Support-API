package com.riteshbkadam.tms.repository;

import com.riteshbkadam.tms.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
}
