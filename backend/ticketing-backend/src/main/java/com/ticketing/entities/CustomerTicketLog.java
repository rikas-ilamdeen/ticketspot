package com.ticketing.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_ticket_logs")
public class CustomerTicketLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId; // Reference to the Customer entity via ID

    @Column(nullable = false)
    private int ticketsPurchased;

    @Column(nullable = false)
    private LocalDateTime purchaseTime;

    // Default constructor
    public CustomerTicketLog() {
    }

    // Parameterized constructor
    public CustomerTicketLog(Long customerId, int ticketsPurchased, LocalDateTime purchaseTime) {
        this.customerId = customerId;
        this.ticketsPurchased = ticketsPurchased;
        this.purchaseTime = purchaseTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getTicketsPurchased() {
        return ticketsPurchased;
    }

    public void setTicketsPurchased(int ticketsPurchased) {
        this.ticketsPurchased = ticketsPurchased;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    // toString() Method
    @Override
    public String toString() {
        return "CustomerTicketLog{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", ticketsPurchased=" + ticketsPurchased +
                ", purchaseTime=" + purchaseTime +
                '}';
    }
}
