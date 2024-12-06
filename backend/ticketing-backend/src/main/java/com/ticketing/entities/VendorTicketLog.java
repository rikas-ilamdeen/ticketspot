package com.ticketing.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_ticket_logs")
public class VendorTicketLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_id", nullable = false)
    private Long vendorId; // Reference to the Vendor entity via ID

    @Column(nullable = false)
    private int ticketsAdded;

    @Column(nullable = false)
    private LocalDateTime additionTime;

    // Default constructor
    public VendorTicketLog() {
    }

    // Parameterized constructor
    public VendorTicketLog(Long vendorId, int ticketsAdded, LocalDateTime additionTime) {
        this.vendorId = vendorId;
        this.ticketsAdded = ticketsAdded;
        this.additionTime = additionTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public int getTicketsAdded() {
        return ticketsAdded;
    }

    public void setTicketsAdded(int ticketsAdded) {
        this.ticketsAdded = ticketsAdded;
    }

    public LocalDateTime getAdditionTime() {
        return additionTime;
    }

    public void setAdditionTime(LocalDateTime additionTime) {
        this.additionTime = additionTime;
    }

    // toString() Method
    @Override
    public String toString() {
        return "VendorTicketLog{" +
                "id=" + id +
                ", vendorId=" + vendorId +
                ", ticketsAdded=" + ticketsAdded +
                ", additionTime=" + additionTime +
                '}';
    }
}
