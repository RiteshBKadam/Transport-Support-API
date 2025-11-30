package com.riteshbkadam.tms.entity;

import com.riteshbkadam.tms.utils.AvailableTrucks;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transporter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transporterId;
    private String companyName;
    private double rating;
    @ElementCollection
    @CollectionTable(
            name ="transporter_available_trucks",
            joinColumns = @JoinColumn(name = "transporter_id")
    )
    private List<AvailableTrucks> availableTrucks = new ArrayList<>();

}
