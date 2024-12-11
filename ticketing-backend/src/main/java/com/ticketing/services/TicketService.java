package com.ticketing.services;

import com.ticketing.entities.Ticket;
import com.ticketing.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // Retrieve the first ticket record (assuming there's only one)
    public Optional<Ticket> getConfiguration() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.isEmpty() ? Optional.empty() : Optional.of(tickets.get(0)); // Get the first record
    }

    public Ticket saveConfiguration(Ticket ticket) {
        ticket.setTicketId(1L);
        // Ensure only one configuration exists
        ticketRepository.save(ticket);
        sendEventUpdate(ticket);
        return ticket;

    }

    public void sendEventUpdate(Ticket event) {
        // Send updated event data to the WebSocket topic
        System.out.println("Sending event update: " + event); // Log event

        messagingTemplate.convertAndSend("/topic/event", event);
    }
}
