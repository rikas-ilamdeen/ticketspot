package com.ticketing.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTicketLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CustomerLogId;
    private Long CustomerId;
    private Long ticketId;
    private int boughtTicketCount;
    private LocalDateTime bookingDate;
    private double totalAmount;
}
