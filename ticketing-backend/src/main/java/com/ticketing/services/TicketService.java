package com.ticketing.services;

import com.ticketing.entities.Ticket;
import com.ticketing.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // Save a new ticket configuration or update an existing one
    public void saveConfiguration(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    // Retrieve the current ticket configuration
    public Optional<Ticket> getConfiguration() {
        return ticketRepository.findById(1L);  // Assuming one configuration is stored, always fetch the record with id 1
    }
}
