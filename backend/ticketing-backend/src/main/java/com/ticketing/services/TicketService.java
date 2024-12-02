package com.ticketing.services;

import com.ticketing.entities.Customer;
import com.ticketing.entities.Ticket;
import com.ticketing.entities.Vendor;
import com.ticketing.repositories.CustomerRepository;
import com.ticketing.repositories.TicketRepository;
import com.ticketing.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Ticket createTicket(Long vendorId, Ticket ticket) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        ticket.setVendor(vendor);
        ticket.setAvailable(true); // Newly created tickets are available by default
        return ticketRepository.save(ticket);
    }

    public Ticket bookTicket(Long ticketId, Long customerId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        if (!ticket.isAvailable()) {
            throw new IllegalStateException("Ticket is already booked.");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ticket.setCustomer(customer);
        ticket.setAvailable(false);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAvailableTickets() {
        return ticketRepository.findByIsAvailable(true);
    }

    public Ticket updateTicket(Long ticketId, Ticket ticketDetails) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setTitle(ticketDetails.getTitle());
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticketRepository.delete(ticket);
    }
}

