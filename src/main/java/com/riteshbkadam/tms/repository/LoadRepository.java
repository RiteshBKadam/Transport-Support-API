package com.riteshbkadam.tms.repository;

import com.riteshbkadam.tms.entity.Load;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoadRepository extends JpaRepository<Load, UUID> {

}
