package com.ticketing.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    @Column(nullable = false,unique = true)

    private String eventName;
    private double price;
    @Column(nullable = false)
    private int totalTickets;
    @Column(nullable = false)
    private int ticketReleaseRate;
    @Column(nullable = false)
    private int customerRetrievalRate;
    @Column(nullable = false)
    private int maxTicketCapacity;
    private String eventDate;
    private String eventTime;

}