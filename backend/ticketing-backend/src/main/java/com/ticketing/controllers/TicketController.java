package com.ticketing.controllers;

import com.ticketing.entities.Ticket;
import com.ticketing.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    /**
     * Endpoint to create a new ticket associated with a specific vendor.
     * @param vendorId The ID of the vendor creating the ticket.
     * @param ticket The ticket details provided in the request body.
     * @return ResponseEntity containing the created ticket.
     */
    @PostMapping("/vendor/{vendorId}")
    public ResponseEntity<?> createTicket(@PathVariable Long vendorId, @RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(vendorId, ticket);
        return ResponseEntity.ok(createdTicket);
    }

    /**
     * Endpoint to book a ticket for a specific customer.
     * @param ticketId The ID of the ticket to be booked.
     * @param customerId The ID of the customer booking the ticket.
     * @return ResponseEntity containing the booked ticket details.
     */
    @PostMapping("/{ticketId}/book/customer/{customerId}")
    public ResponseEntity<?> bookTicket(@PathVariable Long ticketId, @PathVariable Long customerId) {
        Ticket bookedTicket = ticketService.bookTicket(ticketId, customerId);
        return ResponseEntity.ok(bookedTicket);
    }

    /**
     * Endpoint to retrieve a list of all available tickets.
     * @return ResponseEntity containing a list of available tickets.
     */
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableTickets() {
        List<Ticket> tickets = ticketService.getAvailableTickets();
        return ResponseEntity.ok(tickets);
    }

    /**
     * Endpoint to update the details of a specific ticket.
     * @param ticketId The ID of the ticket to be updated.
     * @param ticket The updated ticket details provided in the request body.
     * @return ResponseEntity containing the updated ticket.
     */
    @PutMapping("/{ticketId}")
    public ResponseEntity<?> updateTicket(@PathVariable Long ticketId, @RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketService.updateTicket(ticketId, ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    /**
     * Endpoint to delete a specific ticket.
     * @param ticketId The ID of the ticket to be deleted.
     * @return ResponseEntity with no content if the deletion is successful.
     */
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.noContent().build();
    }
}

