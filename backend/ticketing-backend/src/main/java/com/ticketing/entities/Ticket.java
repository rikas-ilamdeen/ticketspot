package com.ticketing.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true) // Nullable for unbooked tickets
    private Customer customer;

    @Column(nullable = false)
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @Column(nullable = false)
    private boolean isAvailable;

    public Ticket() {
    }

    public Ticket(Long id, Vendor vendor, Customer customer, String title, boolean isAvailable) {
        this.id = id;
        this.vendor = vendor;
        this.customer = customer;
        this.title = title;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

